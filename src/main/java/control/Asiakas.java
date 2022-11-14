package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Asiakkaat;
import model.dao.Dao;

@WebServlet("/asiakas/*")
public class Asiakas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Asiakas() {
        System.out.println("Asiakas.Asiakas()");
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakas.doGet()");
		Dao dao = new Dao();
		ArrayList<Asiakkaat> asiakkaat = dao.getAllItems();
		String hakusana = request.getParameter("hakusana");
		String strJSON = "";
		System.out.println(strJSON);
		if(hakusana!=null) {
			if(!hakusana.equals("")) {
				asiakkaat = dao.getAllItems(hakusana); 							
			}else {
				asiakkaat = dao.getAllItems();
			}
			strJSON = new Gson().toJson(asiakkaat);	
		}	
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakas.doPost()");
	}


	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakas.doPut()");
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakas.doDelete()");
	}

}
