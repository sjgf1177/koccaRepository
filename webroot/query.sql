CREATE TABLE [TZ_STOLDHST] (
 [SUBJ] [varchar] (10) COLLATE Korean_Wansung_CI_AS NOT NULL ,
 [YEAR] [char] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
 [SUBJSEQ] [varchar] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
 [USERID] [varchar] (20) COLLATE Korean_Wansung_CI_AS NOT NULL ,
 [NAME] [varchar] (20) COLLATE Korean_Wansung_CI_AS NULL ,
 [GRCODE] [varchar] (7) COLLATE Korean_Wansung_CI_AS NULL ,
 [ORGLECCODE] [varchar] (17) COLLATE Korean_Wansung_CI_AS NULL ,
 [SCORE] [decimal](5, 2) NULL ,
 [ISGRADUATED] [char] (1) COLLATE Korean_Wansung_CI_AS NULL ,
 [EDUSTART] [varchar] (12) COLLATE Korean_Wansung_CI_AS NULL ,
 [EDUEND] [varchar] (12) COLLATE Korean_Wansung_CI_AS NULL ,
 [SERNO] [varchar] (30) COLLATE Korean_Wansung_CI_AS NULL ,
 CONSTRAINT [PK_TZ_STOLDHST] PRIMARY KEY  CLUSTERED 
 (
  [SUBJ],
  [YEAR],
  [SUBJSEQ],
  [USERID]
 )  ON [PRIMARY] 
) ON [PRIMARY]
GO 



insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('chr021','2005','0009','a1234','����','N000001','REcochrchr0210509',60,'Y','200509050000','200510252355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cpl001','2004','0004','a2020111','�ȸ���','N000001','REcocplcpl0010404',40,'N','200405240000','200407040000') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cpl029','2005','0005','a2249','�ɹ̿�','N000001','REcocplcpl0290505',72,'Y','200501310000','200503062355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cpr013','2005','0008','a304109','��̿�','N000001','REcocprcpr0130508',0,'N','200505020000','200612312355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cbu022','2005','0009','a304109','��̿�','N000001','REcocbucbu0220509',88,'Y','200509020000','200510252355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cbu022','2004','0004','a9398','�ȼ���','N000001','REcocbucbu0220404',27,'N','200407190000','200408292355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cpr042','2004','0001','aar79','�Ⱦƶ�','N000001','REcocprcpr0420401',31,'N','200501310000','200503062355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('chr020','2004','0004','ab5409','����ȣ','N000001','REcochrchr0200404',21,'N','200407190000','200408292355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('chr020','2005','0008','ab5409','����ȣ','N000001','REcochrchr0200508',9,'N','200509050000','200510252355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('chr020','2005','0009','ab5409','����ȣ','N000001','REcochrchr0200509',37,'N','200512050000','200602132355') 

insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) values('cla036','2005','0004','ab5409','����ȣ','N000001','REcoclacla0360504',0,'N','200505020000','200612312355')  