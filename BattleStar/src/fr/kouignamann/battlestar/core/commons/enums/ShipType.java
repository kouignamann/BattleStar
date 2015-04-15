package fr.kouignamann.battlestar.core.commons.enums;

import org.lwjgl.util.vector.Vector3f;

public enum ShipType {
	
	FIGHTER							(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Fighter"),
//	MRX22_RECON_FLYER				(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/MRX22 Recon Flyer"),
//	GX7_INTERCEPTOR					(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/GX7 interceptor"),
	WRAITH_RAIDER_STARSHIP			(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Wraith Raider Starship"),
//	JUSTIGUE_LEAGUE_FLYING_VEHICLE	(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/justigue league flying vehicle"),
//	HN_48_FLYING_CAR				(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/HN 48 Flying Car"),
//	KAMERI_EXPLORER_FLYING			(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Kameri explorer flying"),
	A6M_ZERO						(new Vector3f(20.0f, 20.0f, 20.0f)	, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/A6M_ZERO"),
//	FEDERATION_INTERCEPTOR_HN48		(new Vector3f(1.0f, 1.0f, 1.0f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Federation Interceptor HN48"),
	A_12_AVENGER_II					(new Vector3f(30.0f, 30.0f, 30.0f)	, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/A-12_Avenger_II"),
//	QUAD_45FG_TRANSPORT				(new Vector3f(0.8f, 0.8f, 0.8f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Quad 45fg transport"),
	DRONE							(new Vector3f(90.0f, 90.0f, 90.0f)	, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/Drone"),
	LEGO_NABOOSHIP					(new Vector3f(0.4f, 0.4f, 0.4f)		, new Vector3f(1.0f, 1.0f, 1.0f)		, "resources/model/LEGO_NabooShip");
	
	/**
	 * NOT WORKING : 
	 * -> "resources/model/SciFi AirShip"						:: nothing happens
	 * -> "resources/model/gunship"								:: nothing happens
	 * -> "resources/model/ARC170"								:: index error
	 * -> "resources/model/A-12_Avenger_II"						:: map_Kddddd /Glass_Cockpit.png
	 * -> "resources/model/Hammer Head Patrol Cruiser"			:: index error
	 * -> "resources/model/X-17 Viper"							:: nothing happens
	 * -> "resources/model/Star Wars FALCON"					:: nothing happens
	 * -> "resources/model/Anakin's PodRacer"					:: index error
	 * -> "resources/model/A10"									:: no mtl
	 * -> "resources/model/B2_Spirit"							:: DDS texture format ???
	 * -> "resources/model/star wars x-wing"					:: nothing happens
	 * -> "resources/model/E-TIE-I"								:: nothing happens
	 * -> "resources/model/P-51 Mustang"						:: no mtl
	 * -> "resources/model/Proton"								:: no mtl
	 * -> "resources/model/Leonardo Da Vinci Flying Machine"	:: index error
	 * -> "resources/model/IconA5"								:: no mtl + TIF img
	 */
	
	private Vector3f scaleVector;
	
	private Vector3f rotationVector;
	
	private String resourcePath;
	
	private ShipType(Vector3f scaleVector, Vector3f rotationVector, String resourcePath) {
		this.scaleVector = scaleVector;
		this.rotationVector = rotationVector;
		this.resourcePath = resourcePath;
	}
	
	public Vector3f getScaleVector() {
		return scaleVector;
	}
	
	public Vector3f getRotationVector() {
		return rotationVector;
	}
	
	public String getResourcePath() {
		return resourcePath;
	}
}
