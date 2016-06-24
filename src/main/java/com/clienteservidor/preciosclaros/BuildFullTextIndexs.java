package com.clienteservidor.preciosclaros;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BuildFullTextIndexs implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
    private SessionFactory sessionFactory;

	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			Session session  = sessionFactory.getCurrentSession();
		    FullTextSession fullTextSession = Search.getFullTextSession(session);
		    fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
		}
	}


}