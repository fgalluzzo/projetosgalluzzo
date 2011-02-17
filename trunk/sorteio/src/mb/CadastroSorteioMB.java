package mb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import job.Sortear;
import modelo.Participante;
import modelo.Sorteio;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.CriaHash;
import util.MessagesReader;
import util.PersistenceUtil;
import config.Config;
import dao.ParticipacaoDao;
import dao.SorteioDao;

@ManagedBean(name = "cadastroSorteioMB")
@SessionScoped
public class CadastroSorteioMB implements Serializable {
	
	
	private Sorteio sorteio;
	private String filtroNome;
	private SorteioDao sorteioDao;
	private String horaInicio;
	private String horaFim;

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
	public String preAlterar() {		
		Integer minuto;
		Integer hora;
		String h;
		String min;
		hora = sorteio.getDataInicio().get(Calendar.HOUR_OF_DAY);
		minuto = sorteio.getDataInicio().get(Calendar.MINUTE);
		if(hora <10) {
			h = "0"+hora.toString();
		} else {
			h = hora.toString();
		}
		if(minuto < 10) {
			min = "0"+minuto.toString();
		} else {
			min = minuto.toString();
		}
		horaInicio = h +":" +min;
		
		hora = sorteio.getDataFim().get(Calendar.HOUR_OF_DAY);
		minuto = sorteio.getDataFim().get(Calendar.MINUTE);
		if(hora <10) {
			h = "0"+hora.toString();
		} else {
			h = hora.toString();
		}
		if(minuto < 10) {
			min = "0"+minuto.toString();
		} else {
			min = minuto.toString();
		}
		horaFim =  h +":" +min;
		return "alterarSorteio";
	}
	public String preIncluirIndex() {
		ultimaPagina = "index";
		sorteio = new Sorteio();
		horaInicio = new String();
		horaFim = new String();
		return "criaSorteio";
	}

	public String preIncluir() {
		sorteio = new Sorteio();
		horaInicio = new String();
		horaFim = new String();
		return "criaSorteio";
	}

	public String alterar() {
		sorteioDao = new SorteioDao(PersistenceUtil.getEntityManager());
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		sorteio.getDataInicio().setTimeZone(timeZone);
		sorteio.getDataFim().setTimeZone(timeZone);
		sorteio.getDataInicio().set(sorteio.getDataInicio().get(Calendar.YEAR),
				sorteio.getDataInicio().get(Calendar.MONTH),
				sorteio.getDataInicio().get(Calendar.DAY_OF_MONTH),
				Integer.parseInt(horaInicio.substring(0, 2)),
				Integer.parseInt(horaInicio.substring(3)));
		
		sorteio.getDataFim().set(sorteio.getDataFim().get(Calendar.YEAR),
				sorteio.getDataFim().get(Calendar.MONTH),
				sorteio.getDataFim().get(Calendar.DAY_OF_MONTH),
				Integer.parseInt(horaFim.substring(0, 2)),
				Integer.parseInt(horaFim.substring(3)));
		
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			JobDetail job = sched.getJobDetail(sorteio.getId().toString(),
					Config.JOBGROUP);
			if (job == null) {

				job = new JobDetail(sorteio.getId().toString(), Config.JOBGROUP,
						Sortear.class);
			}
			sched.deleteJob(sorteio.getId().toString(), Config.JOBGROUP);

			SimpleTrigger disparo = new SimpleTrigger(Config.TRIGGER
					+ sorteio.getId().toString(), Config.JOBGROUP,
					sorteio.getDataFimD());

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
				log.error("Erro ao alterar sorteio :");
				log.error("ERRO " + e.getMessage());
				log.error("CAUSA " + e.getCause());

				message.setDetail(MessagesReader.getMessages().getProperty(
						"infoAlteracaoErro"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"infoAlteracaoErro"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

			}

		} catch (Exception e) {
			Logger log = LoggerFactory.getLogger("INFO ALTERACAO");
			log.error("Erro no agendamento do sorteio usuario :");
			log.error("ERRO " + e.getMessage());
			log.error("CAUSA " + e.getCause());
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

				sched.deleteJob(sorteio.getId().toString(), Config.JOBGROUP);

			} catch (Exception e) {
				FacesMessage message = new FacesMessage();
				message.setDetail(MessagesReader.getMessages().getProperty(
						"erroExclusao"));
				message.setSummary(MessagesReader.getMessages().getProperty(
						"erroExclusao"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, message);
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
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroExclusao"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"erroExclusao"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
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
		return "listaSorteio?faces-redirect=true";
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
			sorteio.setSorteado(false);
			sorteio.getDataInicio().setTimeZone(timeZone);
			sorteio.getDataFim().setTimeZone(timeZone);
			sorteio.getDataInicio().set(sorteio.getDataInicio().get(Calendar.YEAR),
					sorteio.getDataInicio().get(Calendar.MONTH),
					sorteio.getDataInicio().get(Calendar.DAY_OF_MONTH),
					Integer.parseInt(horaInicio.substring(0, 2)),
					Integer.parseInt(horaInicio.substring(3)));
			
			sorteio.getDataFim().set(sorteio.getDataFim().get(Calendar.YEAR),
					sorteio.getDataFim().get(Calendar.MONTH),
					sorteio.getDataFim().get(Calendar.DAY_OF_MONTH),
					Integer.parseInt(horaFim.substring(0, 2)),
					Integer.parseInt(horaFim.substring(3)));
			
			
			sorteioDao.createSorteio(sorteio);
			sorteio.setCodigo(CriaHash.SHA1(loginMB.getUsuario().getGrupo()
					+ "" + sorteio.getId()));
			sorteioDao.update(sorteio);
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			JobDetail job = new JobDetail(sorteio.getId().toString(), Config.JOBGROUP,
					Sortear.class);
			SimpleTrigger disparo = new SimpleTrigger(Config.TRIGGER
					+ sorteio.getId().toString(), Config.JOBGROUP,
					sorteio.getDataFimD());
			sched.scheduleJob(job, disparo);

		} catch (Exception e) {
			sorteio = new Sorteio();
			FacesMessage message = new FacesMessage();
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroCadastroSorteio"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"erroCadastroSorteio"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(null, message);
			e.printStackTrace();
			return null;
		}
		return "sorteioCriado";
	}

	public String resortear() {
		ParticipacaoDao participacaoDao = new ParticipacaoDao(
				PersistenceUtil.getEntityManager());
		List<Participante> ganhadores = participacaoDao
				.sorteiaParticipanteSorteio(sorteio);
		sorteio.setGanhadores(ganhadores);
		sorteio.setSorteado(true);
		FacesMessage message = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			sorteioDao.update(sorteio);
			message.setDetail(MessagesReader.getMessages().getProperty(
					"infoResorteioSucesso"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"infoResorteioSucesso"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);

			context.addMessage(null, message);
		} catch (Exception e) {
			message.setDetail(MessagesReader.getMessages().getProperty(
					"erroResorteioSucesso"));
			message.setSummary(MessagesReader.getMessages().getProperty(
					"erroResorteioSucesso"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(null, message);
		}
		return null;
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
				+ "/participar/?sorteio=" + sorteio.getCodigo();
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
				+ "/participar/?embed=s&sorteio=" + sorteio.getCodigo();
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

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

}
