package ru.javaops.masterjava.xml.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import ru.javaops.masterjava.xml.LoadXML;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10)
public class FileUploadServlet extends HttpServlet {

  private final static String UPLOAD_DIR = "in";


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/WEB-INF/jsp/fileUpload.jsp").forward(req, res);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {
    String inFile=loadFile(req);
    if (!inFile.isEmpty()) {
      try {
        LoadXML.load(inFile);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    res.sendRedirect("/users");
  }

  private String loadFile(HttpServletRequest req) throws IOException, ServletException {
    Collection<Part> parts = req.getParts();
    String applicationPath = req.getServletContext().getRealPath("");
    String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
    String result="";

    File fileSaveDir = new File(uploadFilePath);
    if (!fileSaveDir.exists()) {
      fileSaveDir.mkdirs();
    }

    for (File file : fileSaveDir.listFiles()) {
      if (file.isFile()) {
        file.delete();
      }
    }
    for (Part part : parts) {
      if (part.getSubmittedFileName().isEmpty()) {
        System.out.println("File not found");
        continue;
      }
      result=uploadFilePath + File.separator + part.getSubmittedFileName();
      part.write(result);
      System.out
          .println("Uploaded " + uploadFilePath + File.separator + part.getSubmittedFileName());
    }
    return result;
  }
}
