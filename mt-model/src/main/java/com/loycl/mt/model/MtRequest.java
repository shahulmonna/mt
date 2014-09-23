package com.loycl.mt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author: Syed Shahul
 */
@XmlRootElement(name = "mtRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@Document(collection = "mtRequest")
public class MtRequest implements Serializable {

	private static final long serialVersionUID = -3081516183308198744L;
	@Id
	private String id;
	/**
	 * mobile_no
	 * msg
	 * reference_no
	 */
	private String mobileNo;
	private String msg;
	private String referenceNo;

	/**
	 * Default constructor is required for JAXB conversion.
	 */
	public MtRequest() {
	}

	public MtRequest(String mobileNo, String msg, String referenceNo) {
		this.mobileNo = mobileNo;
		this.msg = msg;
		this.referenceNo = referenceNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	@Override
	public String toString() {
		return "MtRequest{" +
				"mobileNo='" + mobileNo + '\'' +
				", msg='" + msg + '\'' +
				", referenceNo=" + referenceNo +
				'}';
	}
}
