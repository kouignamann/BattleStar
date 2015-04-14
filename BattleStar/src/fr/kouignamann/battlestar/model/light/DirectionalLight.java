package fr.kouignamann.battlestar.model.light;

public class DirectionalLight {
	
	// Color
    private float[] rgb = new float[] {1f,1f,1f};
    
    // Direction
    private float[] xyz = new float[] {0f, 0f, -1f};
    
    // Intensity
    private float ambientIntensity = 0.1f;

    private int rgbLocation= 0;
    private int xyzLocation= 0;
    private int ambientIntensityLocation= 0;
    
    public DirectionalLight(int rgbLocation, int xyzLocation, int ambientIntensityLocation) {
    	super();
    	assert rgbLocation>0 && xyzLocation>0 && ambientIntensityLocation>0;
    	this.rgbLocation = rgbLocation;
    	this.xyzLocation = xyzLocation;
    	this.ambientIntensityLocation = ambientIntensityLocation;
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

	public int getRgbLocation() {
		return rgbLocation;
	}

	public int getXyzLocation() {
		return xyzLocation;
	}

	public int getAmbientIntensityLocation() {
		return ambientIntensityLocation;
	}
}
