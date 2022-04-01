package com.credu.temp;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

@SuppressWarnings("unchecked")
public class CreateFolderBean {

	public ArrayList selectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            
            /*
            sql.append("  select substr(starting, 1, instr(starting, '/', -1)) as starting	\n");
	        sql.append("    from (                                                          \n");
			sql.append("          select replace(starting, '/contents', '') as starting     \n");
			sql.append("            from tz_subjlesson                                      \n");
			sql.append("           where subj like 'T%'                                   	\n");
	        sql.append("    )                                                               \n");
	        */
            
            sql.append("  select seq, vodurl, vod_path, lecnm, substr(vodurl, instr(vodurl, '/', 1, 3), instr(substr(vodurl, instr(vodurl, '/', 1, 3), length(vodurl)), '/', -1)) as dirpath \n");
	        sql.append("    from tz_goldclass                                                                       \n");
	        sql.append("   where seq in (                                                                           \n");
	        sql.append("  100,101,102,103,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,  \n");
	        sql.append("  127,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,153,154,179,180,181,182,  \n");
	        sql.append("  183,184,185,186,187,188,189,189,191,192,193,194,195,196,197,198,199,200,201,202,203,204,  \n");
	        sql.append("  205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,  \n");
	        sql.append("  227,228,229,230,231,232,233,234,235,236,237,238,239,240,240,242,243,244,245,246,247,248,  \n");
	        sql.append("  249,250,250,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,  \n");
	        sql.append("  271,272,273,274,275,276,277,278,279,280,281,282,283,284,285,286,287,288,289,290,291,292,  \n");
	        sql.append("  293,294,295,296,297,298,299,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,  \n");
	        sql.append("  315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,  \n");
	        sql.append("  337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,  \n");
	        sql.append("  359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,  \n");
	        sql.append("  381,382,383,385,386,387,388,389,390,391,392,393,394,395,396,397,398,399,400,401,402,403,  \n");
	        sql.append("  404,405,406,407,408,409,410,411,412,413,414,415,416,417,418,419,420,421,422,423,424,425,  \n");
	        sql.append("  426,427,428,429,430,431,432,433,434,435,436,437,439,442,445,448,452,455,458,461,464,467,  \n");
	        sql.append("  470,472,476,83,84,85,86,87,88,89,90,91,92,94,95,96,97,98,99)                              \n");
	        sql.append("   order by seq                                                                             \n");

            ls = connMgr.executeQuery(sql.toString());
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }
}
