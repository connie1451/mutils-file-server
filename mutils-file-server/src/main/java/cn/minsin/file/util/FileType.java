package cn.minsin.file.util;

/**
 * 	魔数对照表 TODO:尚未完善
 * @author minsin
 *
 */
public enum FileType {

	/** JPEG */
	JPEG("FFD8FF",true),

	/** PNG */
	PNG("89504E47",true),

	/** GIF */
	GIF("47494638",true),

	/** TIFF */
	TIFF("49492A00",false),

	/** Windows bitmap */
	BMP("424D",false),

	/** CAD */
	DWG("41433130",false),

	/** Adobe photoshop */
	PSD("38425053",false),

	/** Rich Text Format */
	RTF("7B5C727466",false),

	/** XML */
	XML("3C3F786D6C",false),

	/** HTML */
	HTML("68746D6C3E",false),

	/** Outlook Express */
	DBX("CFAD12FEC5FD746F",false),

	/** Outlook */
	PST("2142444E",false),

	/** doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db */
	OLE2("0xD0CF11E0A1B11AE1",false),

	/** Microsoft Word/Excel */
	XLS_DOC("D0CF11E0",false),

	/** Microsoft Access */
	MDB("5374616E64617264204A",false),

	/** Word Perfect */
	WPB("FF575043",false),

	/** Postscript */
	EPS_PS("252150532D41646F6265",false),

	/** Adobe Acrobat */
	PDF("255044462D312E",true),

	/** Windows Password */
	PWL("E3828596",false),

	/** ZIP Archive */
	ZIP("504B0304",false),

	/** ARAR Archive */
	RAR("52617221",false),

	/** WAVE */
	WAV("57415645",true),

	/** AVI */
	AVI("41564920",true),

	/** Real Audio */
	RAM("2E7261FD",true),

	/** Real Media */
	RM("2E524D46",true),

	/** Quicktime */
	MOV("6D6F6F76",true),

	/** Windows Media */
	ASF("3026B2758E66CF11",false),

	/** MIDI */
	MID("4D546864",false);

	private String value;
	
	private boolean canPreview;

	public boolean isCanPreview() {
		return canPreview;
	}

	public void setCanPreview(boolean canPreview) {
		this.canPreview = canPreview;
	}

	private FileType(String value,boolean canPreview) {
		this.value = value;
		this.canPreview = canPreview;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
