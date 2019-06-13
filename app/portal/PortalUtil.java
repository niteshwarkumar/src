/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.portal;

import app.db.ConnectionFactory;
import app.extjs.actions.DocService;
import app.extjs.vo.Upload_Doc;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.QuoteService;
import app.resource.Resource;
import app.resource.ResourceService;
import app.standardCode.RandomUtil;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import app.util.TemplateUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class PortalUtil {

    public static PortalUtil INSTANCE = null;

    public PortalUtil() {
    }

    public static synchronized PortalUtil getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new PortalUtil();
        }
        return INSTANCE;
    }

    public static String WIKI_URL_FOLDER = "https://excelnet.xltrans.com/wiki/";
    public static String WIKI_URL_FOLDER_PATH = "C://Program Files/Apache Software Foundation/Tomcat 7.0/webapps/wiki/";
//    public static String WIKI_URL_FOLDER = "/Users/abhisheksingh/Desktop/";
//    public static String WIKI_URL_FOLDER_PATH = "/Users/abhisheksingh/Desktop/";

    public List<String> getAllFiles(String folderPath) {
        List<String> results = new ArrayList<String>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add(listOfFiles[i].getName().replaceFirst("[.][^.]+$", ""));

            }
        }
        return results;

    }

    public boolean isFileDup(String filename, String folderPath) {
        List<String> fileinFolder = getAllFiles(folderPath);
        return fileinFolder.contains(filename);
    }

    public String getWikiText(Integer projectId, String type) {

        Wiki wiki = getSingleWikiNotes(projectId, type);
        String wikiText = "<p></p>";
        if (null != wiki) {
            wikiText = wiki.getNotes();
        }
//        Upload_Doc newDoc = DocService.getInstance().getUploadDoc(projectId, "WIKIPROJ");
//        if(null != newDoc){
//            try{
//                String filename = PortalUtil.WIKI_URL_FOLDER_PATH+newDoc.getPath()+".html";
//                File f = new File(filename);
//                if(f.exists()){
//                    Document document = Jsoup.parse(f, StandardCharsets.UTF_8.name());
//                    Element exTxt = document.select("excel").first();
//                    wikiText = exTxt.toString().replaceAll("<excel>", "").replaceAll("</excel>", "");
//                }
//            }catch(Exception e){
//            }
//        }
        return wikiText;
    }

    public Wiki getSingleWikiQA(Integer id, String type) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Wiki as wiki where wiki.id = ? and type = ?",
                    new Object[]{id, type},
                    new Type[]{Hibernate.INTEGER, Hibernate.STRING});

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        if (results.isEmpty()) {
            return null;
        } else {
            return (Wiki) results.get(0);
        }

    }

    public List<Wiki> getQAWiki(Integer projectId, String type) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Wiki as wiki where wiki.projectId = ? and wiki.type = ?",
                    new Object[]{projectId, type},
                    new Type[]{Hibernate.INTEGER, Hibernate.STRING});

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;

    }

    Wiki getSingleWikiNotes(Integer projectId, String type) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Wiki as wiki where wiki.projectId = ? and wiki.type = ?",
                    new Object[]{projectId, type},
                    new Type[]{Hibernate.INTEGER, Hibernate.STRING});

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        if (results.isEmpty()) {
            return null;
        } else {
            return (Wiki) results.get(0);
        }

    }

    private Object getMainBody(Project p) {
        String templateMainName = "projectWikiBody";

        Template template = null;
        Context context = TemplateUtil.getVelocityContext();
        try {
            context.put("_FILELIST_", getFileList(p, "PROJWIKI"));
            context.put("_QALIST_", getQAList(p, "WIKIGEN"));
            context.put("_EXCELNOTE_", PortalUtil.getInstance().getWikiText(p.getProjectId(), "PROJWIKINOTE"));
            context.put("MAINBODY", true);
            context.put("_PROJECT_NUMBER_", p.getNumber()+p.getCompany().getCompany_code());
            if(isFtpChecked(p.getProjectId(), "PROJWIKINOTE"))
                context.put("FTP", true);
            template = TemplateUtil.getInstance().getTemplate(templateMainName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String body = writer.toString();
        return body;
    }

    private String getLinBody(Project p) {
        String templateLinName = "projectWikiBody";
        Template template = null;
        String body = "";
        Context context = TemplateUtil.getVelocityContext();
        try {
            context.put("_FILELIST_", getFileList(p, "PROJWIKILIN"));
            context.put("_QALIST_", getQAList(p, "WIKIGENLIN"));
            context.put("_EXCELNOTE_", PortalUtil.getInstance().getWikiText(p.getProjectId(), "PROJWIKINOTELIN"));

            context.put("LINBODY", true);
            context.put("_PROJECT_NUMBER_", p.getNumber()+p.getCompany().getCompany_code());
             if(isFtpChecked(p.getProjectId(), "PROJWIKINOTELIN"))
                context.put("FTP", true);
            template = TemplateUtil.getInstance().getTemplate(templateLinName);

        } catch (Exception e) {
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        body = writer.toString();
        return body;

    }

    private String getDtpBody(Project p) {
        String templateDTPName = "projectWikiBody";
        Template template = null;
        String body = "";
        Boolean hasDTP = false;
        Set dtp;
        ProjectService.getInstance().getDTPTaskListforProject(p.getProjectId());
        ProjectService.getInstance().getLinTaskListforProject(p.getProjectId());

        Context context = TemplateUtil.getVelocityContext();

        try {
            context.put("_FILELIST_", getFileList(p, "PROJWIKIDTP"));
            context.put("_QALIST_", getQAList(p, "WIKIGENDTP"));
            context.put("_EXCELNOTE_", PortalUtil.getInstance().getWikiText(p.getProjectId(), "PROJWIKINOTEDTP"));
            context.put("DTPBODY", true);
            context.put("_PROJECT_NUMBER_", p.getNumber()+p.getCompany().getCompany_code());
            if(isFtpChecked(p.getProjectId(), "PROJWIKINOTEDTP"))
                context.put("FTP", true);
            template = TemplateUtil.getInstance().getTemplate(templateDTPName);

        } catch (Exception e) {
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        body = writer.toString();
        return body;

    }

    private List<Map> getFileList(Project p, String identofier) {
        List<Map> FILE_LIST = new ArrayList<>();

        List fileList = QuoteService.getInstance().getUploadDocList(p.getProjectId(), identofier);
        String folderPath = StandardCode.getInstance().getPropertyValue("web.upload.path");
        for (int i = 0; i < fileList.size(); i++) {
            Map<String, String> flieMap = new HashMap<>();
            Upload_Doc qfile = (Upload_Doc) fileList.get(i);
            flieMap.put("fname", qfile.getTitle());
            flieMap.put("desc", qfile.getDescription());
            if (null != qfile.getPath()) {
                flieMap.put("url", "(<a href=" + folderPath + qfile.getPath() + " target=\"_blank\">" + qfile.getPathname() + "</a>)");
            } else {
                flieMap.put("url", "");
            }
            FILE_LIST.add(flieMap);
        }
        return FILE_LIST;
    }

    private List<Map> getQAList(Project p, String identofier) {

        List<Map> QA_LIST = new ArrayList<>();

        List wikiList = PortalUtil.getInstance().getQAWiki(p.getProjectId(), identofier);
        for (int i = 0; i < wikiList.size(); i++) {
            Map<String, String> flieMap = new HashMap<>();
            Wiki wiki = (Wiki) wikiList.get(i);
            flieMap.put("refe", wiki.getReference());
            flieMap.put("ques", wiki.getQues());
            flieMap.put("ans", wiki.getAns());

            QA_LIST.add(flieMap);
        }
        return QA_LIST;
    }

    public JSONObject getVendorEmail(Integer projectId) {
        JSONObject result = new JSONObject();
        JSONArray linVend = new JSONArray();
        JSONArray dtpVend = new JSONArray();
        JSONArray engVend = new JSONArray();
        Project p = ProjectService.getInstance().getSingleProject(projectId);
        List<Integer> unq = new ArrayList<>();
        for (Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
            SourceDoc sd = (SourceDoc) iterSources.next();

            for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                TargetDoc td = (TargetDoc) iterTargets.next();
                for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                    LinTask lt = (LinTask) iterLintasks.next();
                    if (lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                        if (!unq.contains(r.getResourceId())) {
                            linVend.put(new JSONObject().put("nm", ResourceService.getInstance().getVendorName(r)).put("email", r.getEmail_address1()));
                            unq.add(r.getResourceId());
                        }
                    }

                }
                for (Iterator iterLintasks = td.getDtpTasks().iterator(); iterLintasks.hasNext();) {
                    DtpTask lt = (DtpTask) iterLintasks.next();
                    if (lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                        if (!unq.contains(r.getResourceId())) {
                            dtpVend.put(new JSONObject().put("nm", ResourceService.getInstance().getVendorName(r)).put("email", r.getEmail_address1()));
                            unq.add(r.getResourceId());
                        }
                    }

                }
                for (Iterator iterLintasks = td.getEngTasks().iterator(); iterLintasks.hasNext();) {
                    EngTask lt = (EngTask) iterLintasks.next();
                    if (lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                        if (!unq.contains(r.getResourceId())) {
                            engVend.put(new JSONObject().put("nm", ResourceService.getInstance().getVendorName(r)).put("email", r.getEmail_address1()));
                            unq.add(r.getResourceId());
                        }
                    }

                }
            }

        }
        unq=null;
        result.put("l", linVend);
        result.put("d", dtpVend);
        result.put("e", engVend);

        return result;
    }

    public void removeWiki(Project p, User u) {
        String templateName = "projectWikiDel";
        Context context = TemplateUtil.getVelocityContext();
        Upload_Doc newDoc = DocService.getInstance().getUploadDoc(p.getProjectId(), "WIKIPROJ");
        Template template = null;

        try {

            template = TemplateUtil.getInstance().getTemplate(templateName);

        } catch (Exception e) {
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String content = writer.toString();
        String saveFileName = newDoc.getPath();
        String outFname = PortalUtil.WIKI_URL_FOLDER_PATH + saveFileName + ".html";
        try (BufferedWriter fwriter = new BufferedWriter(new FileWriter(outFname))) {
            fwriter.write(content);
        } catch (IOException ex) {
            Logger.getLogger(AdminCreateProjectWiki.class.getName()).log(Level.SEVERE, null, ex);
        }
        newDoc.setUploadDate(new Date());
        newDoc.setUploadedBy(u.getFirstName() + " " + u.getLastName());
        newDoc.setTitle("DEACTIVATED");

        QuoteService.getInstance().addUpload_Doc(newDoc);

    }

    public void generateWiki(Project p, User u) {
        String saveFileName = RandomUtil.getAlphaNumericString();
//        String inpFname = "C://templates/projectWiki.txt";
        String templateName = "projectWiki";
        String templateMainName = "projectWiki";
        String templateLinName = "projectWiki";
        String templateDTPName = "projectWiki";

        Context context = TemplateUtil.getVelocityContext();
//        inpFname = "/Users/abhisheksingh/Project/templates/projectWiki.txt";
//        outFname = "/Users/abhisheksingh/Desktop/"+saveFileName+".html";

        /*####################################################################################*/
        context.put("_PROJECT_NUMBER_", p.getNumber() + p.getCompany().getCompany_code());
        context.put("_PROJECT_MANAGER_", StandardCode.getInstance().noNull(p.getPm()));
        User pm = UserService.getInstance().getSingleUser(p.getPm_id());
        context.put("_PROJECT_MANAGER_NUM", StandardCode.getInstance().noNull(pm.getWorkPhone()));
        context.put("_PROJECT_MANAGER_EMAIL", StandardCode.getInstance().noNull(pm.getWorkEmail1()));
        context.put("_PROJECT_MANAGER_SKYPE", StandardCode.getInstance().noNull(pm.getSkypeId()));
        context.put("_PRODUCT_", StandardCode.getInstance().noNull(p.getProduct()));
        context.put("_PRODUCTDESCRIPTION_", StandardCode.getInstance().noNull(p.getProductDescription()).replaceFirst(p.getProduct() + "-", ""));

        ProjectService.getInstance().getDTPTaskListforProject(p.getProjectId());
        ProjectService.getInstance().getLinTaskListforProject(p.getProjectId());
        context.put("MAINBODY", getMainBody(p));

        if (ProjectService.getInstance().getDTPTaskListforProject(p.getProjectId()).size() > 0) {
            context.put("DTPBODY", getDtpBody(p));
        } else {
            context.put("DTPBODY", "");
        }
        if (ProjectService.getInstance().getLinTaskListforProject(p.getProjectId()).size() > 0) {
            context.put("LINBODY", getLinBody(p));
        } else {
            context.put("LINBODY", getLinBody(p));
        }

        Template template = null;

        try {

            template = TemplateUtil.getInstance().getTemplate(templateName);

        } catch (Exception e) {
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String content = writer.toString();

        Upload_Doc newDoc;
        newDoc = DocService.getInstance().getUploadDoc(p.getProjectId(), "WIKIPROJ");
        if (null == newDoc) {
            newDoc = new Upload_Doc();

        } else {
            try {

                File exfile = new File(PortalUtil.WIKI_URL_FOLDER_PATH + newDoc.getPath() + ".html");
                Files.deleteIfExists(exfile.toPath());

            } catch (IOException ex) {
                Logger.getLogger(AdminCreateProjectWiki.class.getName()).log(Level.SEVERE, null, ex);
            }
            saveFileName = newDoc.getPath();
        }
        String outFname = PortalUtil.WIKI_URL_FOLDER_PATH + saveFileName + ".html";
        try (BufferedWriter fwriter = new BufferedWriter(new FileWriter(outFname))) {
            fwriter.write(content);
        } catch (IOException ex) {
            Logger.getLogger(AdminCreateProjectWiki.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*####################################################################################*/
//        newDoc = 
        newDoc.setPathname(p.getNumber() + p.getCompany().getCompany_code());
        newDoc.setPath(saveFileName);
        newDoc.setClientID(0);
        newDoc.setType("WIKIPROJ");
        newDoc.setProjectID(p.getProjectId());

        newDoc.setUploadDate(new Date());
        newDoc.setUploadedBy(u.getFirstName() + " " + u.getLastName());
        newDoc.setTitle(p.getNumber() + p.getCompany().getCompany_code());
        newDoc.setOwner(u.getFirstName() + " " + u.getLastName());
        newDoc.setDescription(p.getNumber() + p.getCompany().getCompany_code());
        newDoc.setDocFormat(".html");

        QuoteService.getInstance().addUpload_Doc(newDoc);
    }

    public boolean isWikiActive(Integer projectId) {
        Upload_Doc newDoc = DocService.getInstance().getUploadDoc(projectId, "WIKIPROJ");
        if(newDoc==null) return false;
        return !newDoc.getTitle().equalsIgnoreCase("DEACTIVATED");
    }
    
    public boolean isFtpChecked(Integer projectId, String type) {
       Wiki wiki = getSingleWikiNotes(projectId, type);
       if(wiki==null) return false;

        return StandardCode.getInstance().noNull(wiki.isFtp());
    }
    
    

}
