package com.cgp.graphics.objwriter;


import com.cgp.graphics.entities.Model3D;
import com.cgp.graphics.primitives.Polygon;
import com.cgp.math.vector.Vector2F;
import com.cgp.math.vector.Vector3F;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";


    public static void writeModelToObjFile(String fileName, Model3D model) {

        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            writeVerticesOfModel(printWriter, model.getVertices());
            writeTextureVerticesOfModel(printWriter, model.getTextureVertices());
            writeNormalsOfModel(printWriter, model.getNormals());
            writePolygonsOfModel(printWriter, model.getPolygons());
        } catch (IOException e) {
            throw new ObjWriterException("Error writing model to obj file: " + fileName + " " + e.getMessage());
        }
    }

    protected static void writeVerticesOfModel(PrintWriter printWriter, List<Vector3F> vertices) {
        for (Vector3F vertex : vertices) {
            printWriter.println(OBJ_VERTEX_TOKEN + " " + vertex.getX() + " " + vertex.getY() + " " + vertex.getZ());
        }
        printWriter.println();
    }

    protected static void writeTextureVerticesOfModel(PrintWriter printWriter, List<Vector2F> textureVertices) {
        for (Vector2F vertex : textureVertices) {
            printWriter.println(OBJ_TEXTURE_TOKEN + " " + vertex.getX() + " " + vertex.getY());
        }
        printWriter.println();
    }

    protected static void writeNormalsOfModel(PrintWriter printWriter, List<Vector3F> normals){
        for (Vector3F normal : normals) {
            printWriter.println(OBJ_NORMAL_TOKEN + " " + normal.getX() + " " + normal.getY() + " " + normal.getZ());
        }
        printWriter.println();
    }

    protected static void writePolygonsOfModel(PrintWriter printWriter, List<Polygon> polygons){
        for (Polygon polygon : polygons) {
            printWriter.print(modelsPolygonToFaceForObjFile(polygon.getVertexIndices(), polygon.getTextureVertexIndices(), polygon.getNormalIndices()));
            printWriter.println();
        }
    }

    private static String modelsPolygonToFaceForObjFile(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        StringBuilder objFace = new StringBuilder();
        objFace.append(OBJ_FACE_TOKEN+" ");

        for (int i = 0; i < vertexIndices.size(); i++) {

            if (!textureVertexIndices.isEmpty()) {
                objFace.append(vertexIndices.get(i) + 1).append("/").append(textureVertexIndices.get(i) + 1);
            } else {
                objFace.append(vertexIndices.get(i) + 1);
            }

            if (!normalIndices.isEmpty()) {
                if (textureVertexIndices.isEmpty()) {
                    objFace.append("/");
                }
                objFace.append("/").append(normalIndices.get(i) + 1);
            }

            if (i != vertexIndices.size() - 1) {
                objFace.append(" ");
            }

        }

        return objFace.toString();
    }
}
