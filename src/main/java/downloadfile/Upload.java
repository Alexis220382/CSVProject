package downloadfile;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.ibm.useful.http.*;
import dao.CSVFileDAO;
import dto.CSVFile;
import utils.UtilReadCSVFile;

@WebServlet(name = "upload", urlPatterns = {"/upload"})
public class Upload extends HttpServlet {

	ServletContext context;

	public void init(){
		context = this.getServletContext();
		context.setAttribute("id", "flag");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(context.getAttribute("id") != null) {

			UtilReadCSVFile utilCSVFile = new UtilReadCSVFile();
			CSVFileDAO csvFileDAO = null;
			List<CSVFile> csvFiles;

			try {
				csvFileDAO = new CSVFileDAO();
				String fileDescription = null;
				FileData tempFile = null;
				HttpSession session = request.getSession();

				if (isMultipartFormat(request)) {

					PostData multidata = new PostData(request);

					if (multidata.getParameter("description") != null
							&& multidata.getFileData("file_send") != null) {
						fileDescription = multidata.getParameter("description");
						tempFile = multidata.getFileData("file_send");
					}

					session.setAttribute("fileDescription", fileDescription);
					session.setAttribute("tempFile", tempFile);

					tempFile.setFileName("contacts.csv");

					if (tempFile != null) {
						saveFile(tempFile);
					}

					csvFiles = utilCSVFile.readCSVFile("d:\\" + tempFile.getFileName());

					List<String> surname = new ArrayList<String>();
					List<String> same_surname = new ArrayList<String>();
					for (CSVFile csvFile : csvFileDAO.getCSVFile()) {
						surname.add(csvFile.getSurname());
					}

					for (CSVFile csvFile : csvFiles) {
						if (surname.contains(csvFile.getSurname())) {
							csvFileDAO.setCSVFile(csvFile);
							same_surname.add(csvFile.getSurname());
						} else {
							csvFileDAO.addDataFromCSVFile(csvFile);
						}
						if (!surname.contains(csvFile.getSurname())) {
							request.setAttribute("no", "не ");
						}
					}

					request.setAttribute("updated", same_surname);

					if (tempFile != null) {
						removeFile(tempFile);
					}

					request.getRequestDispatcher("index.jsp").forward(request, response);

				}
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				csvFileDAO.conCloseCSVFile();
			}
		}else{
			request.setAttribute("message", "В настоящий момент идет работа с файлом. Пожалуйста, попробуйте повторить позже.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	private boolean isMultipartFormat(HttpServletRequest request) throws ServletException, IOException {
		String temptype = request.getContentType();
		if(temptype.indexOf("multipart/form-data") != -1){
			return true;
		}else{
			return false;
		}
	}

	private void saveFile(FileData tempFile) throws IOException {
		File f;
		String fileName = tempFile.getFileName();
		f = new File("d:\\" + fileName);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(tempFile.getByteData());
		fos.close();
	}

	private void removeFile(FileData tempFile) throws IOException {
		File f;
		String fileName = tempFile.getFileName();
		f = new File("d:\\" + fileName);
		f.delete();
	}
}
