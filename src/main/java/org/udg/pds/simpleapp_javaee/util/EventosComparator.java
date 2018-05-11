package org.udg.pds.simpleapp_javaee.util;

import java.util.Comparator;

import org.udg.pds.simpleapp_javaee.model.Evento;

public class EventosComparator implements Comparator<Evento> {
    @Override
    public int compare(Evento e1, Evento e2) {
    	
    	// Si tienen el mismo estado ordenamos por fecha
    	if ((e1.getEstado().getId().equals(e2.getEstado().getId()))  || (!esFaseFinal(e1.getEstado().getId()) && !esFaseFinal(e2.getEstado().getId())) || (esFaseFinal(e1.getEstado().getId()) && esFaseFinal(e2.getEstado().getId()))  ) {
    		return e1.getFechaEvento().compareTo(e2.getFechaEvento());
    	}
    	else {
    		if (esFaseFinal(e1.getEstado().getId()) && !esFaseFinal(e2.getEstado().getId())) return 1;
    		else if (!esFaseFinal(e1.getEstado().getId()) && esFaseFinal(e2.getEstado().getId())) return -1;
    		else return 0;
    	}

    }
    
    private boolean esFaseFinal(Long idEstado) {
    	return idEstado.equals(Global.EVENTO_FINALIZADO) || idEstado.equals(Global.EVENTO_SUSPENDIDO);
    }

}