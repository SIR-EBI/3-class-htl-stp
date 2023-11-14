import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.*;

public class Datenstroeme {

    public static void main(String[] args) {
        String inputFilename = "main/cli/datenstroeme/resources/in.txt";
        String outputilename = "main/cli/datenstroeme/resources/out.txt";

        writeData(getBinaryString(inputFilename), outputilename);

        writeMapListOfData(inputFilename, "main/cli/datenstroeme/resources/test.txt");
    }

    public static void writeMapListOfData(String inputFilename, String outputFilename) {
        try (AsciiInputStream ais = new AsciiInputStream(inputFilename)) {
            Map<String, List<String>> returnMap = new LinkedHashMap<>();
            List<Character> typeList = new ArrayList<>();
            String line;
            while ((line = ais.readLine()) != null) {
                if (tryIfInt(line)) {
                    List<String> list;
                    if (returnMap.get("Integer") == null) {
                        list = new ArrayList<>();
                    }
                    else {
                        list = returnMap.get("Integer");
                    }

                    list.add(line);
                    returnMap.put("Integer", list);
                    typeList.add('i');
                }
                else if (tryIfDouble(line)) {
                    List<String> list;
                    if (returnMap.get("Double") == null) {
                        list = new ArrayList<>();
                    }
                    else {
                        list = returnMap.get("Double");
                    }

                    list.add(line);
                    returnMap.put("Double", list);
                    typeList.add('d');
                }
                else if (line.equals("true") || line.equals("false")) {
                    List<String> list;
                    if (returnMap.get("Boolean") == null) {
                        list = new ArrayList<>();
                    }
                    else {
                        list = returnMap.get("Boolean");
                    }

                    list.add(line);
                    returnMap.put("Boolean", list);
                    typeList.add('b');
                }
            }

            Map<Character, String> typeMap = new LinkedHashMap<>();
            typeMap.put('i', "Integer");
            typeMap.put('d', "Double");
            typeMap.put('b', "Boolean");

            FileOutputStream fos = new FileOutputStream(outputFilename);

            for (char type : typeList) {
                fos.write((returnMap.get(typeMap.get(type)).get(0) + "\n").getBytes());
                returnMap.get(typeMap.get(type)).remove(0);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode writeMapListOfData");
        }
    }

    public static void writeData(String binaryStringData, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(binaryStringData.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode writeData");
        }
    }

    public static String getBinaryString(String filename) {
        try (AsciiInputStream ais = new AsciiInputStream(filename)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = ais.readLine()) != null) {
                if (tryIfInt(line)) {
                    builder.append("i" + line);
                }
                else if (tryIfDouble(line)) {
                    builder.append("d" + line);
                }
                else if (line.equals("true") || line.equals("false")) {
                    builder.append(line.substring(0, 1));
                }
            }
            return builder.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR with methode getBinaryString");
        }
    }

    public static boolean tryIfInt(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean tryIfDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
