import javax.swing.*;
import java.io.*;

public class OutputStringHTMLCode {
    private String stringOut;

    public String getStringOUT() {
        return stringOut;
    }

    public OutputStringHTMLCode(String inp, String receiverColor, String senderColor){
        StringBuffer output = new StringBuffer();
        output.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">").
                append("<html><head><title>QIP History</title>").
                append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\">").
                append("<meta http-equiv=\"Page-Enter\" content=\"blendTrans(Duration=0.2)\"></head><body>").
                append("<STYLE type=\"text/css\">.receiver {font-size: 7pt; color: ").append(receiverColor).append("; font-weight: bold; margin-top: 10; margin-left:30px; margin-right:100px;}").
                append(".sender   {font-size: 7pt; color: ").append(senderColor).append("; font-weight: bold; margin-top: 10; margin-left:30px; margin-right:100px;}").
                append(".message  {font-size: 7pt; color: black; margin-top: 10; margin-left:30px; margin-right:100px;}").
                append("</STYLE><font face=\"Verdana, Arial, Helvetica, sans-serif\">");

        FileInputStream streamIN = null;
        Reader r = null;
        try {
            streamIN = new FileInputStream(inp);
            r = new InputStreamReader(streamIN, "Cp1251");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedEncodingException y) {
            JOptionPane.showMessageDialog(null, "UnsupportedEncodingException", "Error", JOptionPane.ERROR_MESSAGE);
        }
        BufferedReader br = new BufferedReader(r);

        int cycle = 1;
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("--------->-")) {
                    cycle = 1;
                    output.append("</div>").append("<div CLASS=\"receiver\"><hr>");
                } else if (line.contains("---------<-")) {
                    cycle = 1;
                    output.append("</div>").append("<div CLASS=\"sender\"><hr>");
                } else if (cycle == 2) {
                    output.append(line).append("</div>");
                } else if (cycle == 3) {
                    output.append("<div CLASS=\"message\">").append(line);
                } else {
                    output.append("<br>").append(line);
                }
                cycle++;
                //JOptionPane.showMessageDialog(null, output.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            streamIN.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOException", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "NullPointerException", "Error", JOptionPane.ERROR_MESSAGE);
        }
        output.append("</font></body></HTML>");
        stringOut = output.toString();
    }

   public void writeInFile(String fileOUT)
            throws java.io.IOException {
        FileOutputStream streamOUT = null;
        try {
            streamOUT = new FileOutputStream(fileOUT);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Writer wr = new OutputStreamWriter(streamOUT, "Cp1251");
        wr.write(stringOut);
        wr.close();
    }

}