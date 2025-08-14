package MemberCardGenerator;
import java.io.*;
import java.util.*;
/*This tool reads member info in members.csv to generate the code for member section of about.html, and stores it into member-cards-output.txt
Using guide:
1) download the latest csv update from https://docs.google.com/spreadsheets/d/1NkuFuSVa_5iLQlFsg63C-WsyWvCe_EMYNSIt49TKx3M/edit?gid=0#gid=0 and rename into members.csv
2) run MemberCardGenerator.java
3) manually check the generated member-cards-output.txt and paste into about.html
*/
public class MemberCardGenerator {
    public static void main(String[] args) {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("members.csv"));
            PrintWriter writer = new PrintWriter("member-cards-output.txt", "UTF-8");
            
            String line;
            boolean firstLine = true;
            
            while ((line = csvReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header row
                    continue;
                }
                
                String[] member = parseCSVLine(line);
                
                // Ensure we have enough fields
                if (member.length >= 9) {
                    String memberCard = generateMemberCard(
                        member[0].trim(), // Full Name
                        member[2].trim(), // Team/Position
                        member[8].trim()  // Bio
                    );
                    writer.println(memberCard);
                    writer.println(); // Empty line between cards
                }
            }
            
            csvReader.close();
            writer.close();
            System.out.println("Member cards generated successfully!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        
        result.add(currentField.toString());
        
        return result.toArray(new String[0]);
    }
    
    private static String generateMemberCard(String name, String role, String bio) {
        // Escape HTML special characters
        name = escapeHtml(name);
        role = escapeHtml(role);
        bio = escapeHtml(bio);
        
        return String.format(
            "                <div class=\"member-card\">\n" +
            "                    <div class=\"member-photo\">\n" +
            "                        👤\n" +
            "                    </div>\n" +
            "                    <h3>%s</h3>\n" +
            "                    <p class=\"member-role\">%s</p>\n" +
            "                    <p class=\"member-bio\">%s</p>\n" +
            "                </div>",
            name, role, bio
        );
    }
    
    //safety function to handle html/js special character errors
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}