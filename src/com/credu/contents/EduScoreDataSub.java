//**********************************************************
//1. ��      ��: �н�����DataSub
//2. ���α׷���: EduScoreDataSub.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 08. 26
//7. ��      ��:
//
//**********************************************************
package com.credu.contents;


public class EduScoreDataSub {

	private	String	datatype;
	private	double weight     	=0; 	//����ġ
	private	double score      	=0;		//������
	private	double avscore    	=0;		//����ġ��������
	private double gradscore    =0;     //�̼���������

	public EduScoreDataSub() {};

	public EduScoreDataSub(String d, double w, double s, double a, double g) {
		this();
		this.datatype=d;
		this.weight =w;
		this.score =s;
		this.avscore=a;
		this.gradscore=g;
	};

	/**
	 * @return
	 */
	public double getAvscore() {
		return avscore;
	}

	/**
	 * @return
	 */
	public String getDatatype() {
		return datatype;
	}

	/**
	 * @return
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return
	 */
	public double getGradscore() {
		return gradscore;
	}

	/**
	 * @param d
	 */
	public void setAvscore(double d) {
		avscore = d;
	}

	/**
	 * @param string
	 */
	public void setDatatype(String string) {
		datatype = string;
	}

	/**
	 * @param d
	 */
	public void setScore(double d) {
		score = d;
	}

	/**
	 * @param d
	 */
	public void setWeight(double d) {
		weight = d;
	}

	/**
	 * @param d
	 */
	public void setGradscore(double g) {
		gradscore = g;
	}

}
