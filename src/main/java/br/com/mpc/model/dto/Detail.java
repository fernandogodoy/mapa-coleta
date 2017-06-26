package br.com.mpc.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author Fernando
 *
 */
public class Detail {

	private String text;

	private Long value;

	public void setText(String text) {
		this.text = text;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public Long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("text", text).append("value", value)
				.toString();
	}
	
	
}
