package com.IA.decision.multiAgents.BO;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class OCC {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@OneToOne
	private Agent agent;
	

	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public double getJoy() {
		return joy;
	}
	public void setJoy(double joy) {
		this.joy = joy;
	}
	public double getDistress() {
		return distress;
	}
	public void setDistress(double distress) {
		this.distress = distress;
	}
	public OCC()
	{
		
	}
	
	private double joy;
	private double distress;
	private double shame;
	private double hope;
	private double fear;
	private double satisfaction;
	private double fearConfirmed;
	private double disappointment;
	private double relief;

	public List<OCCsTowardsAgent> getHappyFor() {
		return happyFor;
	}
	public void setHappyFor(List<OCCsTowardsAgent> happyFor) {
		this.happyFor = happyFor;
	}
	public List<OCCsTowardsAgent> getSorryFor() {
		return sorryFor;
	}
	public void setSorryFor(List<OCCsTowardsAgent> sorryFor) {
		this.sorryFor = sorryFor;
	}
	public List<OCCsTowardsAgent> getGloating() {
		return gloating;
	}
	public void setGloating(List<OCCsTowardsAgent> gloating) {
		this.gloating = gloating;
	}
	public List<OCCsTowardsAgent> getPity() {
		return pity;
	}
	public void setPity(List<OCCsTowardsAgent> pity) {
		this.pity = pity;
	}

	@OneToMany
	private List<OCCsTowardsAgent> happyFor;
	@OneToMany
	private List<OCCsTowardsAgent> sorryFor;
	@OneToMany
	private List<OCCsTowardsAgent> gloating;
	@OneToMany
	private List<OCCsTowardsAgent> pity;
	
	public double getShame() {
		return shame;
	}
	public void setShame(double shame) {
		this.shame = shame;
	}
	private double proud;

	public double getProud() {
		return proud;
	}
	public void setProud(double proud) {
		this.proud = proud;
	}
	public double getHope() {
		return hope;
	}
	public void setHope(double hope) {
		this.hope = hope;
	}
	public double getFear() {
		return fear;
	}
	public void setFear(double fear) {
		this.fear = fear;
	}
	public double getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(double satisfaction) {
		this.satisfaction = satisfaction;
	}
	public double getFearConfirmed() {
		return fearConfirmed;
	}
	public void setFearConfirmed(double fearConfirmed) {
		this.fearConfirmed = fearConfirmed;
	}
	public double getDisappointment() {
		return disappointment;
	}
	public void setDisappointment(double disappointment) {
		this.disappointment = disappointment;
	}
	public double getRelief() {
		return relief;
	}
	public void setRelief(double relief) {
		this.relief = relief;
	}
}
