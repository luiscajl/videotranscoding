package es.urjc.videotranscoding.entities;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import es.urjc.videotranscoding.codecs.ConversionType;

@Entity
@Table(name = "conversion",schema = "videotranscoding")
public class Conversion {
	public interface Basic {
	}

	public interface Details {
	}

	/**
	 * Id for the conversionId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Basic.class)
	private Integer id;
	/**
	 * Name of the video
	 */
	@JsonView(Basic.class)
	private String name;
	/**
	 * Path of the conversion of the video
	 */
	@JsonView(Details.class)
	private String path;
	/**
	 * Progress of the conversion
	 */
	@JsonView(Details.class)
	private String progress = "0";
	/**
	 * True if videoConversion is finish, false eoc
	 */
	@JsonView(Details.class)
	private boolean finished;
	/**
	 * Active if is in process of conversion, eoc false
	 */
	@JsonView(Details.class)
	private boolean active;
	/**
	 * File Size of the file
	 */
	@JsonView(Details.class)
	private String fileSize;
	/**
	 * Type of conversion for this video
	 */
	@JsonView(Basic.class)
	@Column(name = "conversion_type")
	private ConversionType conversionType;
	/**
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "original_id", referencedColumnName = "id", nullable = false)
	private Original parent;

	/**
	 * Constructor for hibernate
	 */
	protected Conversion() {
	}

	/**
	 * Constructor more simple
	 * 
	 * @param conversion
	 *            for the originalVideo
	 * @param originalVideo
	 *            of the master video
	 */
	public Conversion(ConversionType conversion, Original original) {
		this.name = original.getName() + "-" + System.currentTimeMillis();
		this.conversionType = conversion;
		this.parent = original;

	}

	/**
	 * Constructor for the diferent name for the video
	 * 
	 * @param name
	 *            for the conversionVideo
	 * @param conversion
	 *            for the video
	 */
	public Conversion(String name, ConversionType conversion) {
		this.name = name;
		this.conversionType = conversion;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public ConversionType getConversionType() {
		return conversionType;
	}

	public void setConversionType(ConversionType conversionType) {
		this.conversionType = conversionType;
	}

	public String getProgress() {
		return progress.replace(",", ".");
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileSize() {
		if (isActive()) {
			return fileSize.replace(",", ".");
		} else if (isFinished()) {
			return (getSizeMB(new File(getPath())) + " MB").replace(",", ".");
		} else {
			return "";
	}}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getConversionId() {
		return id;
	}

	public Original getParent() {
		return parent;
	}

	private String getSizeMB(File f) {
		double fileSizeInBytes = f.length();
		double fileSizeInKB = fileSizeInBytes / 1024;
		double fileSizeInMB = fileSizeInKB / 1024;
		return String.format("%.2f", fileSizeInMB);
	}

}
