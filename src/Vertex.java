public class Vertex {

    private float[] xyzw = new float[] { 0f, 0f, 0f, 1f };
    private float[] rgba = new float[] { 1f, 1f, 1f, 1f };

    public static final int elementCount = 8;
    public static final int elementBytes = 4;
    public static final int sizeInBytes = elementBytes * elementCount;

    public Vertex(float x, float y, float z, float r, float g, float b) {
        setXYZ(x, y, z);
        setRGB(r, g, b);
    }

    public void setXYZ(float x, float y, float z) {
        this.setXYZW(x, y, z, 1f);
    }

    public void setRGB(float r, float g, float b) {
        this.setRGBA(r, g, b, 1f);
    }

    public void setXYZW(float x, float y, float z, float w) {
        this.xyzw = new float[] { x, y, z, w };
    }

    public void setRGBA(float r, float g, float b, float a) {
        this.rgba = new float[] { r, g, b, 1f };
    }

    public float[] getXYZW() {
        return new float[] { this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3] };
    }

    public float[] getRGBA() {
        return new float[] { this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3] };
    }
}