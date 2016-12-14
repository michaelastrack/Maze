package org.launchcode.maze.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table (name = "user")
public class User {
	
	private String username;
	private String pwHash;
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private int uid;
	private int fails;
	private int completions;
	
	public User () {}
	
	public User (String username, String password) {
		this.username = username;
		this.pwHash = hashPassword(password);
		this.fails = 0;
		this.completions = 0;
	}
	
	@Id
	@GeneratedValue
	@NotNull
	@Column (name = "uid", unique = true)
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@NotNull
	@Column (name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	@Column (name = "pwhash")
	public String getPwHash() {
		return pwHash;
	}

	public void setPwHash(String pwHash) {
		this.pwHash = pwHash;
	}

	public int getFails() {
		return fails;
	}

	public void setFails(int fails) {
		this.fails = fails;
	}

	public int getCompletions() {
		return completions;
	}

	public void setCompletions(int completions) {
		this.completions = completions;
	}

	public boolean isMatchingPassword(String password) {
		return encoder.matches(password, pwHash);
	}
	
	public static boolean isValidPassword(String password) {
		Pattern validUsernamePattern = Pattern.compile("(\\S){5,20}");
		Matcher matcher = validUsernamePattern.matcher(password);
		return matcher.matches();
	}
	
	public static boolean isValidUsername(String username) {
		Pattern validUsernamePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{4,11}");
		Matcher matcher = validUsernamePattern.matcher(username);
		return matcher.matches();
	}
	
	private static String hashPassword(String password) {		
		return encoder.encode(password);
	}
	
	public void complete () {
		this.setCompletions((this.getCompletions() + 1));
	}
	
	public void fail () {
		this.setFails((this.getFails() + 1));
	}
	
	public int percentage () {
		int percent;
		double total = this.getFails() + this.getCompletions();
		total = this.getCompletions()/total;
		percent = (int) (total * 100);
		return percent;
	}
	
	public static void main(String[] args) {
		

	}

}
