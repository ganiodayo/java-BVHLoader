package fr.mimus.bvh.model.test;

public class Face {
	private final int[] vertexIndices = {-1, -1, -1};
    private final int[] normalIndices = {-1, -1, -1};
    private int[] texturesIndices = {-1, -1, -1};
    
    public Face(int[] vertexIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
    }

    public Face(int[] vertexIndices, int[] normalIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
    }
    
    public Face(int[] vertexIndices, int[] normalIndices, int[] texturesIndices) {
        this.vertexIndices[0] = vertexIndices[0];
        this.vertexIndices[1] = vertexIndices[1];
        this.vertexIndices[2] = vertexIndices[2];
        this.normalIndices[0] = normalIndices[0];
        this.normalIndices[1] = normalIndices[1];
        this.normalIndices[2] = normalIndices[2];
        this.texturesIndices[0] = texturesIndices[0];
        this.texturesIndices[1] = texturesIndices[1];
        this.texturesIndices[2] = texturesIndices[2];
    }
    
    public boolean hasNormals() {
        return normalIndices[0] != -1;
    }
    
    public int[] getVertexIndices() {
        return vertexIndices;
    }

    public int[] getNormalIndices() {
        return normalIndices;
    } 
    
    public int[] getTextureIndices() {
        return texturesIndices;
    }
    
    public void setTextureIndices(int[] texturesIndices) {
    	this.texturesIndices=texturesIndices;
    }
}
