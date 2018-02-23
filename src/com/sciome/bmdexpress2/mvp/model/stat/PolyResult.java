package com.sciome.bmdexpress2.mvp.model.stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolyResult extends StatResult
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8080785183059201658L;

	private int degree;

	public PolyResult()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDegree()
	{
		return degree;
	}

	public void setDegree(int degree)
	{
		this.degree = degree;
	}

	@Override
	public List<String> getColumnNames()
	{
		String polyname = "Linear";
		if (degree > 1)
		{
			polyname = "Poly " + String.valueOf(degree);
		}
		List<String> returnList = new ArrayList<String>(Arrays.asList(polyname + " BMD", polyname + " BMDL",
				polyname + " BMDU", polyname + " fitPValue", polyname + " fitLogLikelihood",
				polyname + " AIC", polyname + " adverseDirection", polyname + " BMD/BMDL", polyname + " Success"));

		for (int i = 0; i < this.curveParameters.length; i++)
			returnList.add(polyname + " Parameter beta_" + i);

		return returnList;

	}

	@Override
	public List<Object> getRow()
	{
		List<Object> returnList = new ArrayList<Object>(Arrays.asList((this.getBMD()), (this.getBMDL()),
				(this.getBMDU()), (this.getFitPValue()), (this.getFitLogLikelihood()), (this.getAIC()),
				(this.getAdverseDirection()), (this.getBMD() / this.getBMDL()), this.getSuccess()));

		for (int i = 0; i < this.curveParameters.length; i++)
		{
			if(curveParameters!=null)
			returnList.add(new Double(this.curveParameters[i]));
			else
				returnList.add(null);
		}

		return returnList;
	}

	@Override
	public String toString()
	{
		String polyname = "Linear";
		if (degree > 1)
		{
			polyname = "Poly " + String.valueOf(degree);
		}
		return polyname;
	}

	@Override
	public List<String> getParametersNames()
	{
		List<String> parameters = new ArrayList<>();

		for (int i = 0; i <= degree; i++)
		{
			parameters.add("beta_" + i);
		}
		return parameters;
	}

}
