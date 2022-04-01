rem server run version
cd D:\eclipse\workspace\KOCCA_DEV\webroot\WEB-INF\classes
java -cp ".;D:/eclipse/workspace/KOCCA_DEV/webroot/WEB-INF/lib;C:/bea/weblogic92/server/lib/ojdbc14.jar" schedule.BannerAdminScheduler
cd D:\eclipse\workspace\KOCCA_DEV\schedule

rem local test version
rem cd D:\work\academy\KOCCA_DEV\webroot\WEB-INF\classes
rem java -cp ".;D:/work/academy/KOCCA_DEV/webroot/WEB-INF/lib;D:/bea/weblogic92/server/lib/ojdbc14.jar" schedule.BannerAdminScheduler
rem cd D:\work\academy\KOCCA_DEV\schedule
