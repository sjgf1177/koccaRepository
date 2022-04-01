package schedule;

import java.util.ArrayList;
import java.util.HashMap;

import com.credu.library.ConfigSet;
import com.credu.library.ErrorManager;
import common.CreateHTMLFile;

public class BannerAdminScheduler {

    public static void main(String args[]) throws Exception {

        BannerAdminSchedulerBean bean = new BannerAdminSchedulerBean();

        try {
            ArrayList<HashMap<String, String>> newbannerList = bean.selectBannerList();

            createBannerHTMLFile(newbannerList);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    /**
     * banner html 파일을 생성한다.
     * 
     * @param bannerList
     * @throws Exception
     */
    private static void createBannerHTMLFile(ArrayList<HashMap<String, String>> bannerList) throws Exception {

        ConfigSet conf = new ConfigSet();
        StringBuffer htmlSb = new StringBuffer();
        HashMap<String, String> dataMap = null;
        HashMap<String, String> fdataMap = null; //fixed : side 배너

        String imageSize = "";
        String imageWidth = "";

        try {
            htmlSb.append("<!-- mainBannerSection -->\n");
            htmlSb.append("<div class=\"mainBannerSection\">\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("    <div class=\"mainbnr\">\n");
            htmlSb.append("        <h2 class=\"blind\">메인배너</h2>\n");
            htmlSb.append("        <div class=\"viewport\">\n");
            htmlSb.append("\n");

            for (int i = 0; i < bannerList.size(); i++) {

                dataMap = bannerList.get(i);

                imageSize = dataMap.get("img_size").toLowerCase();
                imageWidth = (imageSize.equals("w1")) ? "860" : (imageSize.equals("w2") ? "570" : "280");
                if (dataMap.get("fixed_flag").equals("N")) {

                    htmlSb.append("            <ul class=\"group\">\n");
                    htmlSb.append("                <li class=\"").append(imageSize).append("\">\n");
                    htmlSb.append("                    <a href=\"").append(dataMap.get("url")).append("\" target=\"").append(dataMap.get("url_target")).append("\">\n");
                    htmlSb.append("                    <img src=\"").append(dataMap.get("save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(dataMap.get("explain"));
                    htmlSb.append("\" width=\"").append(imageWidth).append("\" height=\"276\" />\n");
                    htmlSb.append("                    </a>\n");
                    htmlSb.append("                </li>\n");
                    htmlSb.append("            </ul>\n");

                } else if (dataMap.get("fixed_flag").equals("Y")) {
                    fdataMap = dataMap;
                }
            }

            htmlSb.append("        </div>\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("\n");
            htmlSb.append("    <!-- mainbnrSide -->\n");
            htmlSb.append("    <div class=\"mainbnrSide\">\n");
            htmlSb.append("\n");
            htmlSb.append("            <ul>\n");
            htmlSb.append("                <li class=\"").append(fdataMap.get("img_size").toLowerCase()).append("\">\n");
            htmlSb.append("                    <a href=\"").append(fdataMap.get("url")).append("\" target=\"").append(fdataMap.get("url_target")).append("\">\n");
            htmlSb.append("                    <img src=\"").append(fdataMap.get("save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(fdataMap.get("explain")).append("\" width=\"280\" height=\"276\" />\n");
            htmlSb.append("                    </a>\n");
            htmlSb.append("                </li>\n");
            htmlSb.append("            </ul>\n");
            htmlSb.append("\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- /mainbnrSide -->\n");
            htmlSb.append("</div>\n");
            htmlSb.append("<!-- /mainBannerSection -->\n");

            String upDir = conf.getProperty("dir.home");

            upDir += "learn\\user\\2013\\portal\\include\\";

            CreateHTMLFile html = new CreateHTMLFile(upDir, "mainbanner.html", htmlSb.toString());
            html.create();

            //            File f = new File(upDir + "mainbanner.html");
            //
            //            if (!f.exists()) {
            //                f.createNewFile();
            //            }
            //
            //            FileWriter fw = new FileWriter(upDir + "mainbanner.html", false);
            //            BufferedWriter bw = new BufferedWriter(fw);
            //
            //            bw.write(htmlSb.toString());
            //            bw.newLine();
            //            bw.close();

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("BannerAdminScheduler()\r\n" + e.getMessage());
        }

    }

}
