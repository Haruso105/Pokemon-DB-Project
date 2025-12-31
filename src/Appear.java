package db;

import java.sql.Date;
import java.sql.Time;

public class Appear {
	
	private int id;
	private int number;
	private String name;
	private String ken;
	private String shi;
	
	private Date date;
	private Time time;
	private String type;
	
	public Appear(int id, int number, String name, String ken, String shi, Date date, Time time) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.ken = ken;
		this.shi = shi;
		this.date = date;
		this.time = time;
	}
	
	public Appear(int id, int number, String name, String ken, String shi, Date date, Time time, String type) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.ken = ken;
		this.shi = shi;
		this.date = date;
		this.time = time;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public int getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public String getKen() {
		return ken;
	}
	public String getShi() {
		return shi;
	}
	public Date getDate() {
		return date;
	}
	public Time getTime() {
		return time;
	}
	
	public String getType() {
		return type;
	}
	
	public String mergeType(String typeName) {
		type = type + "・" + typeName;
		return type;
	}
}
