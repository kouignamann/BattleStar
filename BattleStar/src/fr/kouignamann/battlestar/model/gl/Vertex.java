/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.model.gl;

/**
 *
 * @author Guillaume BERTRAND
 */
public class Vertex {
    
    // DATA
    private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
    private float[] nxyzw = new float[] {1f, 1f, 1f, 1f};
    private float[] rgba = new float[] {1f, 1f, 1f, 1f};
    private float[] st = new float[] {1f, 1f};
    
    //VERTEX CONST
    // The amount of bytes an element has
    public static final int ELEMENT_BYTES = 4;
    
    // Elements per parameter
    public static final int POSITION_ELEMENT_COUNT = 4;
    public static final int NORMAL_ELEMENT_COUNT = 4;
    public static final int COLOR_ELEMENT_COUNT = 4;
    public static final int TEXTURE_ELEMENT_COUNT = 2;

    // Bytes per parameter
    public static final int POSITION_BYTES_COUNT = POSITION_ELEMENT_COUNT * ELEMENT_BYTES;
    public static final int NORMAL_BYTE_COUNT = NORMAL_ELEMENT_COUNT * ELEMENT_BYTES;
    public static final int COLOR_BYTE_COUNT = COLOR_ELEMENT_COUNT * ELEMENT_BYTES;
    public static final int TEXTURE_BYTE_COUNT = TEXTURE_ELEMENT_COUNT * ELEMENT_BYTES;

    // Byte offsets per parameter
    public static final int POSITION_BYTE_OFFSET = 0;
    public static final int NORMAL_BYTE_OFFSET = POSITION_BYTE_OFFSET + POSITION_BYTES_COUNT;
    public static final int COLOR_BYTE_OFFSET = NORMAL_BYTE_OFFSET + NORMAL_BYTE_COUNT;
    public static final int TEXTURE_BYTE_OFFSET = COLOR_BYTE_OFFSET + COLOR_BYTE_COUNT;

    // The amount of elements that a vertex has
    public static final int ELEMENT_COUNT =
                    POSITION_ELEMENT_COUNT +
                    NORMAL_ELEMENT_COUNT +
                    COLOR_ELEMENT_COUNT +
                    TEXTURE_ELEMENT_COUNT;
    public static final int STRIDE =
                    POSITION_BYTES_COUNT +
                    NORMAL_BYTE_COUNT +
                    COLOR_BYTE_COUNT +
                    TEXTURE_BYTE_COUNT;
    
    public float[] getElements() {
        return new float[] {
            xyzw[0], xyzw[1], xyzw[2], xyzw[3],
            nxyzw[0], nxyzw[1], nxyzw[2], nxyzw[3],
            rgba[0], rgba[1], rgba[2], rgba[3],
            st[0], st[1]
        };
    }
    
    public void setXyz(float[] xyz) {
        this.xyzw = new float[] {xyz[0], xyz[1], xyz[2], 1.0f};
    }
    
    public void setXyz(float x, float y, float z) {
        this.setXyz(new float[] {x, y, z});
    }

    public void setNxyz(float[] nxyz) {
        this.nxyzw = new float[] {nxyz[0], nxyz[1], nxyz[2], 1.0f};
    }
    
    public void setNxyz(float x, float y, float z) {
        this.setNxyz(new float[] {x, y, z});
    }

    public void setRgba(float[] rgba) {
        this.rgba = rgba;
    }

    public void setRgba(float r, float g, float b, float a) {
        this.setRgba(new float[] {r, g, b, a});
    }

    public void setSt(float[] st) {
        this.st = st;
    }

    public void setSt(float s, float t) {
        this.setSt(new float[] {s, t});
    }
}
