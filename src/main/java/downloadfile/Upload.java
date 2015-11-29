package downloadfile;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.ibm.useful.http.*;
import dao.CSVFileDAO;
import dao.PagingDAO;
import dto.CSVFile;
import myexception.MyException;
import progress.Progress;
import utils.UtilReadCSVFile;

@WebServlet(name = "upload", urlPatterns = {"/upload"})
public class Upload extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UtilReadCSVFile utilCSVFile = new UtilReadCSVFile();
		CSVFileDAO csvFileDAO = null;
		List<CSVFile> csvFiles;

		try {

			csvFileDAO = new CSVFileDAO();
			if(isMultipartFormat(request)){

				PostData multidata = new PostData(request);

				String fileDescription = null;
				FileData tempFile = null;
				HttpSession session = request.getSession();

				if (multidata.getParameter("description") != null
						&& multidata.getFileData("file_send") != null) {
					fileDescription = multidata.getParameter("description");
					tempFile = multidata.getFileData("file_send");
				}

				session.setAttribute("fileDescription", fileDescription);
				session.setAttribute("tempFile", tempFile);

				tempFile.setFileName("contacts.csv");

				if(tempFile != null){
					saveFile(tempFile);
				}

				csvFiles = utilCSVFile.readCSVFile("d:\\" + tempFile.getFileName());

				List surname = new ArrayList();
				for (int i=0; i<csvFileDAO.getCSVFile().size();i++){
					surname.add(csvFileDAO.getCSVFile().get(i).getSurname());
				}

				for(CSVFile csvFile : csvFiles){
					if(surname.contains(csvFile.getSurname())){
						csvFileDAO.setCSVFile(csvFile);
					}else{
						csvFileDAO.addDataFromCSVFile(csvFile);
					}
				}

				if(tempFile != null){
					removeFile(tempFile);
				}


				request.getRequestDispatcher("index.jsp").forward(request, response);

			}
		} catch (Exception e){
			System.out.println(e.toString());
		}finally {
			csvFileDAO.conCloseCSVFile();
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
		//не всегда имеются права админа
		f = new File("d:\\" + fileName);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(tempFile.getByteData());
		fos.close();
	}

	private void removeFile(FileData tempFile) throws IOException {
		File f;
		String fileName = tempFile.getFileName();
		//не всегда имеются права админа
		f = new File("d:\\" + fileName);
		f.delete();
	}
}
