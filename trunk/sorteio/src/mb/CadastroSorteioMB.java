package mb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import job.Sortear;
import modelo.Sorteio;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;
import dao.SorteioDao;

@ManagedBean(name = "cadastroSorteioMB")
@SessionScoped
public class CadastroSorteioMB {

	private final static String JOBGROUP = "SorteiosWeb";
	private final static String TRIGGER = "SorteiosWeb disparo";
	private Sorteio sorteio;
	private String filtroNome;
	private SorteioDao sorteioDao;
	private String enderecoSorteio;
	private String enderecoSorteioEmbed;
	private TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
	private List<Sorteio> sorteios;
	private String termoBusca;
	private boolean temResultadoBusca;
	private String ultimaPagina;

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public CadastroSorteioMB() {
		sorteio = new Sorteio();
		ultimaPagina = "index";
	}

	public String preIncluirIndex() {
		ultimaPagina = "index";
		sorteio = new Sorteio();
		return "criaSorteio";
	}

	public String preIncluir() {
		sorteio = new Sorteio();
		return "criaSorteio";
	}

	public String alterar() {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			JobDetail job = sched.getJobDetail(sorteio.getId().toString(),
					JOBGROUP);

			sched.deleteJob(sorteio.getId().toString(), JOBGROUP);
			
			SimpleTrigger disparo = new SimpleTrigger(TRIGGER+sorteio.getId().toString(),JOBGROUP,sorteio.getDataFimD());

			sched.scheduleJob(job, disparo);

			try {
				sorteioDao.update(sorteio);
				message.setDetail(MessagesReader.getMessages().getProperty(
						"infoAlteracaoSucesso"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"infoAlteracaoSucesso"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);

				context.addMessage(null, message);
			} catch (Exception e) {
				Logger log = LoggerFactory.getLogger("INFO ALTERACAO");
				log.error("Erro no agendamento do sorteio usuario :");
				log.error("ERRO", e.getStackTrace());
				message.setDetail(MessagesReader.getMessages().getProperty(
						"infoAlteracaoErro"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"infoAlteracaoErro"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

			}

		} catch (Exception e) {
			Logger log = LoggerFactory.getLogger("INFO ALTERACAO");
			log.error("Erro ao alterar usuario :");
			log.error("ERRO", e.getStackTrace());

			message.setDetail(MessagesReader.getMessages().getProperty(
					"infoAlteracaoErro"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"infoAlteracaoErro"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(null, message);
		}
		return null;
	}

	public String preBusca() {
		termoBusca = new String();
		if (sorteios != null && !sorteios.isEmpty())
			sorteios.clear();
		temResultadoBusca = false;
		return "buscarSorteio";
	}

	public String buscar() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		ValueExpression expression = app.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						String.format("#{%s}", "loginMB"), Object.class);
		LoginMB loginMB = (LoginMB) expression.getValue(context.getELContext());
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		sorteios = sorteioDao.buscar(termoBusca, loginMB.getUsuario()
				.getGrupo());
		if (sorteios != null && !sorteios.isEmpty()) {
			temResultadoBusca = true;
		} else {
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"nenhumResultadoEncontrado"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"nenhumResultadoEncontrado"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);

			context.addMessage(null, message);
			temResultadoBusca = false;
		}

		ultimaPagina = "buscarSorteio";
		return null;
	}

	public String excluir() {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		try {
			sorteioDao.excluir(sorteio);

			try {
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();

				sched.deleteJob(sorteio.getId().toString(), JOBGROUP);

			} catch (Exception e) {
				// TODO: handle exception
			}
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"infoExclusaoSucesso"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"infoExclusaoSucesso"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preListar();
		return null;
	}

	public String preListar() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		ValueExpression expression = app.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						String.format("#{%s}", "loginMB"), Object.class);
		LoginMB loginMB = (LoginMB) expression.getValue(context.getELContext());
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		sorteios = sorteioDao.findByGrupo(loginMB.getUsuario().getGrupo());
		ultimaPagina = "listaSorteio";
		return "listaSorteio";
	}

	public String cadastrar() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		ValueExpression expression = app.getExpressionFactory()
				.createValueExpression(context.getELContext(),
						String.format("#{%s}", "loginMB"), Object.class);
		LoginMB loginMB = (LoginMB) expression.getValue(context.getELContext());
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());

		try {

			sorteio.setGrupo(loginMB.getUsuario().getGrupo());
			sorteio.setInscritos(0);
			sorteioDao.createSorteio(sorteio);
			sorteio.setCodigo(CriaHash.SHA1(loginMB.getUsuario().getGrupo()
					+ "" + sorteio.getId()));
			sorteioDao.update(sorteio);
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			JobDetail job = new JobDetail(sorteio.getId().toString(), JOBGROUP,
					Sortear.class);
			SimpleTrigger disparo = new SimpleTrigger(TRIGGER
					+ sorteio.getId().toString(), JOBGROUP,
					sorteio.getDataFimD());
			sched.scheduleJob(job, disparo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			sorteio = new Sorteio();
			e.printStackTrace();
			return "index";
		}
		return "sorteioCriado";
	}

	public Sorteio getSorteio() {
		return sorteio;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}

	public String getEnderecoSorteio() {
		HttpServletRequest request = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		return "http://" + request.getLocalName() + request.getContextPath()
				+ "/?sorteio=" + sorteio.getCodigo();
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public List<Sorteio> getSorteios() {

		return sorteios;
	}

	public void setSorteios(List<Sorteio> sorteios) {
		this.sorteios = sorteios;
	}

	public String getEnderecoSorteioEmbed() {
		HttpServletRequest request = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		return "http://" + request.getLocalName() + request.getContextPath()
				+ "/?embed=s&sorteio=" + sorteio.getCodigo();
	}

	public String getTermoBusca() {
		return termoBusca;
	}

	public void setTermoBusca(String termoBusca) {
		this.termoBusca = termoBusca;
	}

	public boolean isTemResultadoBusca() {
		return temResultadoBusca;
	}

	public void setTemResultadoBusca(boolean temResultadoBusca) {
		this.temResultadoBusca = temResultadoBusca;
	}

	public String getUltimaPagina() {
		return ultimaPagina;
	}

	public void setUltimaPagina(String ultimaPagina) {
		this.ultimaPagina = ultimaPagina;
	}

}
