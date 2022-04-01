<%@page errorPage= "/learn/library/error.jsp" %>
<%@ page import= "java.util.*" %>
<%@ page import= "com.credu.homepage.*" %>
<%@ page import= "com.credu.library.*" %>
<%
    RequestBox mbox = (RequestBox)request.getAttribute("requestbox");
    BannerAdminBean bean= new BannerAdminBean();
    ArrayList<DataBox> bannerList = bean.selectBannerList(box);
    DataBox mdbox = null;
    DataBox sdbox = null;

    //out.print(bannerList.size());
    String process = mbox.getString("p_process");
    int seq = 0;
    String title ="";
    String onoff_flag ="";
    String use_yn ="";
    int sort_order = 0;
    String save_img_nm      = "";
    String img_size         = "";
    String url              = "";
    String url_target       = "";
    String explain          = "";
    String period_yn        = "";
    String fixed_flag       = "";
    int img_width = 0;


%>
<!--
 class 별 이미지 사이즈
 1. w1 : 280 * 275 or 280 * 276
 2. w2 : 570 * 275 or 570 * 276
 3. w3 : 860 * 275 or 860 * 276

 배너 구성 가능 조합
 1. w1
 2. w2 + w3
 3. w3 + w2
 4. w3 + w3 + w3
 -->

            <!-- mainBannerSection -->
            <div class="mainBannerSection">
                <!-- //mainbnr -->
                <div class="mainbnr">
                    <h2 class="blind">메인배너</h2>
                    <div class="viewport">

<%

        if ( bannerList == null || bannerList.size() == 0 ) {
%>
                
                등록된 내용이 없습니다.
                
<%
        } else {

            for(int i = 0; i < bannerList.size(); i++) {

                mdbox = (DataBox)bannerList.get(i);

                seq             = mdbox.getInt("d_seq");
                title           = mdbox.getString("d_title");
                onoff_flag      = mdbox.getString("d_onoff_flag");
                use_yn          = mdbox.getString("d_use_yn");
                sort_order      = mdbox.getInt("d_sort_order");
                save_img_nm     = mdbox.getString("d_save_img_nm").replace("\\", "/");
                img_size        = mdbox.getStringDefault("d_img_size", "W2");
                url             = mdbox.getString("d_url");
                url_target      = mdbox.getString("d_url_target");
                explain         = mdbox.getString("d_explain");
                period_yn       = mdbox.getString("d_period_yn");
                fixed_flag      = mdbox.getString("d_fixed_flag");

                if(img_size.equals("W3")){
                    img_width = 860;
                }else if(img_size.equals("W2")){
                    img_width = 570;
                }else if(img_size.equals("W1")){
                    img_width = 280;
                }

                if(use_yn.equals("Y") && period_yn.equals("Y") && fixed_flag.equals("N") ){

%>

                        <ul class="group">
                            <li class="<%=img_size%>">
                                <a href="<%=url%>" target="<%=url_target%>">
                                <img src="<%=save_img_nm%>" alt="<%=explain%>" width="<%=img_width%>" height="276" />
                                </a>
                            </li>
                        </ul>

<%
                }else if(fixed_flag.equals("Y")){
                    sdbox = (DataBox)bannerList.get(i);
                }
                
            }

%>
            </div>
                </div>
                <!-- //mainbnr -->

                <!-- mainbnrSide -->
                <div class="mainbnrSide">

                        <ul>
                            <li class="<%=sdbox.getString("d_img_size")%>">
                                <a href="<%=sdbox.getString("d_url")%>" target="<%=sdbox.getString("d_url_target")%>">
                                <img src="<%=sdbox.getString("d_save_img_nm")%>" alt="<%=sdbox.getString("d_explain")%>" width="280" height="276" />
                                </a>
                            </li>
                        </ul>

                </div>
                <!-- /mainbnrSide -->
            </div>
            <!-- /mainBannerSection -->
<%
        }

%>