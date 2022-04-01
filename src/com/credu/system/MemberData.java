// **********************************************************
// 1. 제 목: 회원 Data
// 2. 프로그램명 : MemberData.java
// 3. 개 요: 회원 data bean
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2003. 7. 2
// 7. 수 정:
// **********************************************************

package com.credu.system;

/**
 * file name : MemberData.java date : 2003/5/28 programmer: 정상진 (puggjsj@hanmail.net) function : MEMBER DATA
 */

public class MemberData {
    private String userid; // ID
    private String resno; // 주민번호
    private String resno1; // 주민번호1
    private String resno2; // 주민번호2
    private String pwd; // 암호화비민번호
    private String name; // 이름
    private String grcode;
    private String eng_name; // 이름
    private String email; // email
    private String birthday; // 생일
    private String cono; // 사번
    private String authority; // 권한
    private String usergubun; // 사용자구분(0022)
    private String post1; // 우편1
    private String post2; // 우편2
    private String addr; // 주소
    private String addr2; // 상세주소
    private String hometel; // 집전화번호
    private String comp_post1; // 회사우편1
    private String comp_post2; // 회사우편2
    private String comp_addr1; // 회사주소1
    private String comp_addr2; // 회사주소1
    private String handphone; // 휴대전화번호
    private String comptel; // 회사전화번호
    private String tel_line; // 내선번호
    private String comp; // 회사ID
    private String comptext; // 회사명
    private String interest; // 관심사항
    private String recomid; // 추천자ID
    private String ismailing; // 메일수신여부
    private String isopening; // 연락처공개여부
    private String islettering; // 뉴스레터수신여
    private String indate; // 생성일
    private String ldate; // 최종수정일
    private String lglast; // 최종로그인시간
    private String lgip; // 로그인IP
    private String pwd_date; // 패스변경일자
    private String old_pwd; // 구 패스워드
    private String asgn; // 소속코드
    private String asgnnm; // 소속명
    private String jikun; // 직군
    private String jikunnm; // 직군명
    private String jikup; // 직업 -- 사용중
    private String workfieldcd; // 종사분야
    private String jikupnm; // 직급명
    private String jikwi; // 직위
    private String jikwinm; // 직위명
    private String jikmu1; // 직무1
    private String jikmu2; // 직무2
    private String jikmu3; // 직무3
    private String jikmu4; // 직무4
    private String jikmunm; // 직무명
    private String jikchek; // 직책
    private String jikcheknm; // 직책명
    private String jikho; // 직호
    private String jikhonm; // 호봉명
    private String ent_date; // 입사일
    private String grp_ent_date; // 그룹입사일
    private String pmt_date; // 승진일
    private String old_cono; // 구 사번
    private String cono_chg_date; // 사번변경일
    private String office_gbn; // 재직여부
    private String office_gbnnm; // 재직여부명
    private String retire_date; // 퇴사일자
    private String work_plc; // 근무지코드
    private String work_plcnm; // 근무지명
    private String sex; // 성별
    private String photo;
    private String fix_gubun; // 사원 구분
    private String promotion; // 승진대상여부
    private String gubb_prmt; // 승진대상여부
    private String degree; // 학력
    private String degreenm; // 학력

    private String gadmin; // 권한
    private String companynm; // 회사명
    private String gpmnm; // 사업부명
    private String deptnm; // 부서명
    private String compnm; // 회사명 + / + 사업부명 + / + 부서명
    private String isdeptmnger; // 부서장 여부
    private String compgubun; // 회사 구분자

    private String membergubun; // 회원구분 P: 개인 / C: 기업 / U: 대학교
    private String membergubunnm; // 회원구분 P: 개인 / C: 기업 / U: 대학교
    private String registgubun; // 가입구분 KGDI: 게임 / KOCCA: 문콘
    private String registgubunnm; // 가입구분 KGDI: 게임 / KOCCA: 문콘

    private String state; // 사용상태
    private String validation; // 실명인증여부

    private int lgcnt; // 로그인횟수
    private int point; // 마일리지 포인트 점수
    private int dispnum; // 총게시물수
    private int total_page_count; // 게시물총페이지수

    private String registerroutenm;
    private String grcodenm;

    private String job;
    private String job_culture;
    private String jobnm;
    private String job_culturenm;
    private String mobileUserId;

    public String getMobileUserId() {
        return mobileUserId;
    }

    public void setMobileUserId(String mobileUserId) {
        this.mobileUserId = mobileUserId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob_culture() {
        return job_culture;
    }

    public void setJob_culture(String jobCulture) {
        job_culture = jobCulture;
    }

    public String getJobnm() {
        return jobnm;
    }

    public void setJobnm(String jobnm) {
        this.jobnm = jobnm;
    }

    public String getJob_culturenm() {
        return job_culturenm;
    }

    public void setJob_culturenm(String jobCulturenm) {
        job_culturenm = jobCulturenm;
    }

    public MemberData() {
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setResno(String resno) {
        this.resno = resno;
    }

    public String getResno() {
        return resno;
    }

    public void setResno1(String resno1) {
        this.resno1 = resno1;
    }

    public String getResno1() {
        return resno1;
    }

    public void setResno2(String resno2) {
        this.resno2 = resno2;
    }

    public String getResno2() {
        return resno2;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setCono(String cono) {
        this.cono = cono;
    }

    public String getCono() {
        return cono;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setUsergubun(String usergubun) {
        this.usergubun = usergubun;
    }

    public String getUsergubun() {
        return usergubun;
    }

    public void setPost1(String post1) {
        this.post1 = post1;
    }

    public String getPost1() {
        return post1;
    }

    public void setPost2(String post2) {
        this.post2 = post2;
    }

    public String getPost2() {
        return post2;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setHometel(String hometel) {
        this.hometel = hometel;
    }

    public String getHometel() {
        return hometel;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setComptel(String comptel) {
        this.comptel = comptel;
    }

    public String getComptel() {
        return comptel;
    }

    public void setTel_line(String tel_line) {
        this.tel_line = tel_line;
    }

    public String getTel_line() {
        return tel_line;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getComp() {
        return comp;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInterest() {
        return interest;
    }

    public void setRecomid(String recomid) {
        this.recomid = recomid;
    }

    public String getRecomid() {
        return recomid;
    }

    public void setIsmailing(String ismailing) {
        this.ismailing = ismailing;
    }

    public String getIsmailing() {
        return ismailing;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getIndate() {
        return indate;
    }

    public void setLglast(String lglast) {
        this.lglast = lglast;
    }

    public String getLglast() {
        return lglast;
    }

    public void setLgip(String lgip) {
        this.lgip = lgip;
    }

    public String getLgip() {
        return lgip;
    }

    public void setPwd_date(String pwd_date) {
        this.pwd_date = pwd_date;
    }

    public String getPwd_date() {
        return pwd_date;
    }

    public void setOld_pwd(String old_pwd) {
        this.old_pwd = old_pwd;
    }

    public String getOld_pwd() {
        return old_pwd;
    }

    public void setAsgn(String asgn) {
        this.asgn = asgn;
    }

    public String getAsgn() {
        return asgn;
    }

    public void setAsgnnm(String asgnnm) {
        this.asgnnm = asgnnm;
    }

    public String getAsgnnm() {
        return asgnnm;
    }

    public void setJikun(String jikun) {
        this.jikun = jikun;
    }

    public String getJikun() {
        return jikun;
    }

    public void setJikunnm(String jikunnm) {
        this.jikunnm = jikunnm;
    }

    public String getJikunnm() {
        return jikunnm;
    }

    public void setJikup(String jikup) {
        this.jikup = jikup;
    }

    public String getJikup() {
        return jikup;
    }

    public void setWorkfieldcd(String workfieldcd) {
        this.workfieldcd = workfieldcd;
    }

    public String getWorkfieldcd() {
        return workfieldcd;
    }

    public void setJikupnm(String jikupnm) {
        this.jikupnm = jikupnm;
    }

    public String getJikupnm() {
        return jikupnm;
    }

    public void setJikwi(String jikwi) {
        this.jikwi = jikwi;
    }

    public String getJikwi() {
        return jikwi;
    }

    public void setJikwinm(String jikwinm) {
        this.jikwinm = jikwinm;
    }

    public String getJikwinm() {
        return jikwinm;
    }

    public void setJikmu1(String jikmu1) {
        this.jikmu1 = jikmu1;
    }

    public String getJikmu1() {
        return jikmu1;
    }

    public void setJikmu2(String jikmu2) {
        this.jikmu2 = jikmu2;
    }

    public String getJikmu2() {
        return jikmu2;
    }

    public void setJikmu3(String jikmu3) {
        this.jikmu3 = jikmu3;
    }

    public String getJikmu3() {
        return jikmu3;
    }

    public void setJikmu4(String jikmu4) {
        this.jikmu4 = jikmu4;
    }

    public String getJikmu4() {
        return jikmu4;
    }

    public void setJikmunm(String jikmunm) {
        this.jikmunm = jikmunm;
    }

    public String getJikmunm() {
        return jikmunm;
    }

    public void setJikchek(String jikchek) {
        this.jikchek = jikchek;
    }

    public String getJikchek() {
        return jikchek;
    }

    public void setJikcheknm(String jikcheknm) {
        this.jikcheknm = jikcheknm;
    }

    public String getJikcheknm() {
        return jikcheknm;
    }

    public void setJikho(String jikho) {
        this.jikho = jikho;
    }

    public String getJikho() {
        return jikho;
    }

    public void setJikhonm(String jikhonm) {
        this.jikhonm = jikhonm;
    }

    public String getJikhonm() {
        return jikhonm;
    }

    public void setEnt_date(String ent_date) {
        this.ent_date = ent_date;
    }

    public String getEnt_date() {
        return ent_date;
    }

    public void setGrp_ent_date(String grp_ent_date) {
        this.grp_ent_date = grp_ent_date;
    }

    public String getGrp_ent_date() {
        return grp_ent_date;
    }

    public void setPmt_date(String pmt_date) {
        this.pmt_date = pmt_date;
    }

    public String getPmt_date() {
        return pmt_date;
    }

    public void setOld_cono(String old_cono) {
        this.old_cono = old_cono;
    }

    public String getOld_cono() {
        return old_cono;
    }

    public void setCono_chg_date(String cono_chg_date) {
        this.cono_chg_date = cono_chg_date;
    }

    public String getCono_chg_date() {
        return cono_chg_date;
    }

    public void setOffice_gbn(String office_gbn) {
        this.office_gbn = office_gbn;
    }

    public String getOffice_gbn() {
        return office_gbn;
    }

    public void setOffice_gbnnm(String office_gbnnm) {
        this.office_gbnnm = office_gbnnm;
    }

    public String getOffice_gbnnm() {
        return office_gbnnm;
    }

    public void setRetire_date(String retire_date) {
        this.retire_date = retire_date;
    }

    public String getRetire_date() {
        return retire_date;
    }

    public void setWork_plc(String work_plc) {
        this.work_plc = work_plc;
    }

    public String getWork_plc() {
        return work_plc;
    }

    public void setWork_plcnm(String work_plcnm) {
        this.work_plcnm = work_plcnm;
    }

    public String getWork_plcnm() {
        return work_plcnm;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setCompanynm(String companynm) {
        this.companynm = companynm;
    }

    public String getCompanynm() {
        return companynm;
    }

    public void setGpmnm(String gpmnm) {
        this.gpmnm = gpmnm;
    }

    public String getGpmnm() {
        return gpmnm;
    }

    public void setDeptnm(String deptnm) {
        this.deptnm = deptnm;
    }

    public String getDeptnm() {
        return deptnm;
    }

    public void setCompnm(String compnm) {
        this.compnm = compnm;
    }

    public String getCompnm() {
        return compnm;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setFix_gubun(String fix_gubun) {
        this.fix_gubun = fix_gubun;
    }

    public String getFix_gubun() {
        return fix_gubun;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setIsdeptmnger(String isdeptmnger) {
        this.isdeptmnger = isdeptmnger;
    }

    public String getIsdeptmnger() {
        return isdeptmnger;
    }

    public void setGubb_prmt(String gubb_prmt) {
        this.gubb_prmt = gubb_prmt;
    }

    public String getGubb_prmt() {
        return gubb_prmt;
    }

    public void setCompgubun(String compgubun) {
        this.compgubun = compgubun;
    }

    public String getCompgubun() {
        return compgubun;
    }

    public void setLgcnt(int lgcnt) {
        this.lgcnt = lgcnt;
    }

    public int getLgcnt() {
        return lgcnt;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setDispnum(int dispnum) {
        this.dispnum = dispnum;
    }

    public int getDispnum() {
        return dispnum;
    }

    public void setTotalPageCount(int total_page_count) {
        this.total_page_count = total_page_count;
    }

    public int getTotalpagecount() {
        return total_page_count;
    }

    /**
     * @return
     */
    public String getGadmin() {
        return gadmin;
    }

    /**
     * @param string
     */
    public void setGadmin(String string) {
        gadmin = string;
    }

    public String getMembergubun() {
        return membergubun;
    }

    public void setMembergubun(String membergubun) {
        this.membergubun = membergubun;
    }

    public String getRegistgubun() {
        return registgubun;
    }

    public void setRegistgubun(String registgubun) {
        this.registgubun = registgubun;
    }

    public String getMembergubunnm() {
        return membergubunnm;
    }

    public void setMembergubunnm(String membergubunnm) {
        this.membergubunnm = membergubunnm;
    }

    public String getRegistgubunnm() {
        return registgubunnm;
    }

    public void setRegistgubunnm(String registgubunnm) {
        this.registgubunnm = registgubunnm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComp_post1() {
        return comp_post1;
    }

    public void setComp_post1(String comp_post1) {
        this.comp_post1 = comp_post1;
    }

    public String getComp_post2() {
        return comp_post2;
    }

    public void setComp_post2(String comp_post2) {
        this.comp_post2 = comp_post2;
    }

    public String getComp_addr1() {
        return comp_addr1;
    }

    public void setComp_addr1(String comp_addr1) {
        this.comp_addr1 = comp_addr1;
    }

    public String getComp_addr2() {
        return comp_addr2;
    }

    public void setComp_addr2(String comp_addr2) {
        this.comp_addr2 = comp_addr2;
    }

    public String getComptext() {
        return comptext;
    }

    public void setComptext(String comptext) {
        this.comptext = comptext;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegreenm() {
        return degreenm;
    }

    public void setDegreenm(String degreenm) {
        this.degreenm = degreenm;
    }

    public String getIsopening() {
        return isopening;
    }

    public void setIsopening(String isopening) {
        this.isopening = isopening;
    }

    public String getIslettering() {
        return islettering;
    }

    public void setIslettering(String islettering) {
        this.islettering = islettering;
    }

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getLdate() {
        return ldate;
    }

    public void setLdate(String ldate) {
        this.ldate = ldate;
    }

    public String getGrcode() {
        return grcode;
    }

    public void setGrcode(String grcode) {
        this.grcode = grcode;
    }

    public String getRegisterroutenm() {
        return registerroutenm;
    }

    public void setRegisterroutenm(String registerroutenm) {
        this.registerroutenm = registerroutenm;
    }

    public String getGrcodenm() {
        return grcodenm;
    }

    public void setGrcodenm(String grcodenm) {
        this.grcodenm = grcodenm;
    }
}