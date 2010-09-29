package managedBean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="util")
@RequestScoped
public class UtilBean {
	
	private Date hoje;
	
	
	public Date getHoje() {
		return new Date(System.currentTimeMillis());
	}
}
