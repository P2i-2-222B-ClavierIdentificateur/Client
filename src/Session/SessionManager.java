package Session;

import java.util.ArrayList;

import javax.swing.JProgressBar;

import Database.Insert;
import GUI.DatabaseWorkFrame;

/*
 * Objet permettant la gestion des sessions durant l'l'execution du programme  
 */

public class SessionManager {

	private Session currentSession; // session en cours
	private ArrayList<Session> prevSessions; // liste des sessions précédantes

	public SessionManager() {
		prevSessions = new ArrayList<Session>();
		newSession(null);
	}

	// termine une session et l'ajoute à la liste des sessions terminées
	public void endCurrentSession(DatabaseWorkFrame dbp) {
		currentSession.setRunning(false);
		// le succès de la session est défini à partir du succès de la dernière
		// tentative
		if (currentSession.getPasswordTries().size() > 0) {
			currentSession.setSuccess(
					currentSession.getPasswordTries().get(currentSession.getPasswordTries().size() - 1).isSuccess());
			Insert.addSession(currentSession, Main.Main.conn,dbp);
		}
		prevSessions.add(new Session(currentSession, this));
		System.out.println("Ending current session : " + currentSession.isSuccess());

	}

	// créé une nouvelle session en terminant la précédante si elle existe.
	public void newSession(DatabaseWorkFrame dbp) {
		if (currentSession != null) {
			endCurrentSession(dbp);
		}
		System.out.println("New Session");
		currentSession = new Session(this);
		currentSession.getTimeUpdater().start();
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public ArrayList<Session> getPrevSessions() {
		return prevSessions;
	}

	public void setPrevSessions(ArrayList<Session> prevSessions) {
		this.prevSessions = prevSessions;
	}

}