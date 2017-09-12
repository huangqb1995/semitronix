package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import upload.FileUploadTools;
import util.DBConnect;
import util.InsertCSV;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//upload and save files
			String tempDir = getServletContext().getRealPath("/") + "uploadTemp";
			String saveDir = getServletContext().getRealPath("/") + "upload/";
			FileUploadTools fut = new FileUploadTools(request, -1, tempDir);
			List<String> all = fut.saveAll(saveDir);	

			//to solve boostrap-fileupload problem, response with json	
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write("{}");
			
			//connect to database
			Connection conn = null;
			try{
				 conn = DBConnect.getConnection();
				if( conn != null){
					System.out.println("Connect success!");
				}else{
					System.out.println("Connect fail");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//insert data
			InsertCSV inc = new InsertCSV(conn, saveDir);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
