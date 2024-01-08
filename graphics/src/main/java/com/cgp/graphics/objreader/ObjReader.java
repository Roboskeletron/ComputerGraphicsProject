package com.cgp.graphics.objreader;


import com.cgp.graphics.components.GameObject;
import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class ObjReader {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static GameObject read(String fileContent) {
        GameObject result = new GameObject();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.isEmpty()) {
                continue;
            }

            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            ++lineInd;
            switch (token) {
                case OBJ_VERTEX_TOKEN -> result.getVertices().add(parseVertex(wordsInLine, lineInd));
                case OBJ_TEXTURE_TOKEN -> result.getTextureVertices().add(parseTextureVertex(wordsInLine, lineInd));
                case OBJ_NORMAL_TOKEN -> result.getNormals().add(parseNormal(wordsInLine, lineInd));
                case OBJ_FACE_TOKEN -> {
                    result.getPolygons().add(parseFace(wordsInLine, lineInd));
                    if (result.getPolygons().size() > 1 &&
                            (result.getPolygons().get(result.getPolygons().size() - 2).getTextureVertexIndices().size() == 0) !=
                                    (result.getPolygons().get(result.getPolygons().size() - 1).getTextureVertexIndices().size() == 0))
                    {
                        throw new ObjReaderException("Polygon has no texture vertices.", lineInd);
                    }
                }
                default -> {}
            }
        }

        if (result.getPolygons().size() == 0) {
            throw new ObjReaderException("OBJ has not any polygons", lineInd);
        }

        if (result.getVertices().size() == 0) {
            throw new ObjReaderException("OBJ has not any vertices", lineInd);
        }

        for (Polygon list : result.getPolygons()) {
            int vertexIndicesSize = result.getVertices().size();
            int normalIndicesSize = result.getNormals().size();
            int textureIndicesSize = result.getTextureVertices().size();

            for (int i = 0; i < 3; i++) {
                // проверка на то, что индекс вершины полигона не содержится в массиве индексов модели
                int vertexIndex = list.getVertexIndices().get(i);
                // проверка на то, что вершина задана отрицательно
                if (vertexIndex < 0) {
                    list.getVertexIndices().set(i, result.getVertices().size() + 1 + vertexIndex);
                    vertexIndex = list.getVertexIndices().get(i);
                }
                // проверка на vertexIndex < 0 нужна потому, что на прошлом условии мы уже перевели индексы на положительные
                if (vertexIndex >= result.getVertices().size() || vertexIndex < 0) {
                    throw new ObjReaderException("The polygon is specified incorrectly: vertex index out of bounds.",
                            findLineInObj(
                                    new Scanner(fileContent),
                                    OBJ_VERTEX_TOKEN,
                                    vertexIndicesSize));
                }
            }

            // проверка для нормалей
            if (list.getNormalIndices().size() != 0) {
                for (int i = 0; i < 3; i++) {
                    int normalIndex = list.getNormalIndices().get(i);
                    if (normalIndex < 0) {
                        list.getNormalIndices().set(i, result.getNormals().size() + 1 + normalIndex);
                        normalIndex = list.getNormalIndices().get(i);
                    }
                    if (normalIndex >= result.getNormals().size() || normalIndex < 0) {
                        throw new ObjReaderException("The polygon is specified incorrectly: normal index out of bounds.",
                                findLineInObj(
                                        new Scanner(fileContent),
                                        OBJ_NORMAL_TOKEN,
                                        normalIndicesSize));
                    }
                }
            }

            // проверка для текстурных вершин
            if (list.getTextureVertexIndices().size() != 0) {
                for (int i = 0; i < 3; i++) {
                    int textureIndex = list.getTextureVertexIndices().get(i);
                    if (textureIndex < 0) {
                        list.getTextureVertexIndices().set(i, result.getTextureVertices().size() + 1 + textureIndex);
                        textureIndex = list.getTextureVertexIndices().get(i);
                    }
                    if (textureIndex >= result.getTextureVertices().size() || textureIndex < 0) {
                        throw new ObjReaderException("The polygon is specified incorrectly: texture index out of bounds.",
                                findLineInObj(
                                        new Scanner(fileContent),
                                        OBJ_TEXTURE_TOKEN,
                                        textureIndicesSize));
                    }
                }
            }
        }



        return result;
    }

    public static Vector3F parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new ObjReaderException("Too much vertex arguments.", lineInd);
            }

            return new Vector3F(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few vertex arguments.", lineInd);
        }
    }

    public static Vector2F parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 2) {
                if (Math.signum(Float.parseFloat(wordsInLineWithoutToken.get(2))) != 0) {
                    throw new ObjReaderException("Too much texture vertex arguments.", lineInd);
                }
            }
            return new Vector2F(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
        }
    }

    public static Vector3F parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new ObjReaderException("Too much normal arguments.", lineInd);
            }
            return new Vector3F(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few normal arguments.", lineInd);
        }
    }

    protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() < 3) {
            throw new ObjReaderException("Polygon has too few vertices.", lineInd);
        }
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();

        for (String s : wordsInLineWithoutToken) {
            parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
        }

        if (findEqualites(onePolygonVertexIndices)) {
            throw new ObjReaderException("The polygon can`t contain the same vertex indices", lineInd);
        }

        Polygon result = new Polygon();
        result.setVertexIndices(onePolygonVertexIndices);
        result.setTextureVertexIndices(onePolygonTextureVertexIndices);
        result.setNormalIndices(onePolygonNormalIndices);
        return result;
    }

    public static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd) {
        try {
            String[] wordIndices = wordInLine.split("/");
            switch (wordIndices.length) {
                case 1 -> {
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                }
                case 2 -> {
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                }
                case 3 -> {
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
                    if (!wordIndices[1].equals("")) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                    }
                }
                default -> {
                    throw new ObjReaderException("Invalid element size.", lineInd);
                }
            }

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }
    }

    public static int findLineInObj(Scanner scanner, String token, Integer indexInPolygon){
        int lineInd = 0;
        int rows = 1;
        while (scanner.hasNextLine()) {
            lineInd++;
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.isEmpty()) {
                continue;
            }

            final String trueToken = wordsInLine.get(0);
            wordsInLine.remove(0);

            if (token.equals(trueToken)) {
                if (rows != indexInPolygon) {
                    rows++;
                } else {
                    return lineInd;
                }
            }
        }
        return -1;
    }
    public static boolean findEqualites(ArrayList<Integer> onePolygonVertexIndices) {
        for (int i = 0; i < onePolygonVertexIndices.size() - 1; i++) {
            for (int j = i + 1; j < onePolygonVertexIndices.size(); j++) {
                if (Objects.equals(onePolygonVertexIndices.get(i), onePolygonVertexIndices.get(j))) return true;
            }
        }
        return false;
    }
}
