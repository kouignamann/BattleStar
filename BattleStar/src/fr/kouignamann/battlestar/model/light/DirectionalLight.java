package fr.kouignamann.battlestar.model.light;

public class DirectionalLight {
	
	// Color
    private float[] rgb = new float[] {1f,1f,1f};
    
    // Direction
    private float[] xyz = new float[] {0f, 0f, -1f};
    
    // Intensity
    private float ambientIntensity = 0.1f;
    
    public DirectionalLight() {
    	super();
    }
    
    public float[] getRgb() {
    	return rgb;
    }

	public float[] getXyz() {
		return xyz;
	}

	public float getAmbientIntensity() {
		return ambientIntensity;
	}
}
