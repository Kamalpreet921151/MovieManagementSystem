package sait.mms.application;

import sait.mms.manager.MovieManager; 

public class Driver {

	public static void main(String[] args) 
	{
		 MovieManager manager = new MovieManager();
	        manager.loadMovieList();
	        manager.displayMenu();
	    }
	}

	
