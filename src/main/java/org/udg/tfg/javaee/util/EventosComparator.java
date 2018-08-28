package org.udg.tfg.javaee.util;

import java.util.Comparator;
import org.udg.tfg.javaee.model.Evento;

/**
 * Esta clase define la comparación a realizar entre distintos eventos deportivos para ser ordenados en una lista.
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class EventosComparator implements Comparator<Evento> {
	
	
	/**
	* Método encargado de comparar dos eventos deportivos recibidos por parámetro.
	* Se realiza la comparación entre dos eventos deportivos. En caso de que tengan el mismo estado (Abierto,Completo,Finalizado o Suspendido), se comparan por fecha
	* de celebración del evento. En caso de un evento esté en un estado final (Finalizado o suspendido) y el otro en un estado no final (Abierto o Completo) se devuelve como mayor
	* el evento en fase no final.
	* @param e1 Evento deportivo a comparar.
	* @param e2 Evento deportivo a comparar.
	* @return Devuelve 1 si el evento e1 es mayor, -1 si el evento e2 es mayor o 0 si son iguales.
	*/
	@Override
	public int compare(Evento e1, Evento e2) {

		// Si tienen el mismo estado ordenamos por fecha
		if ((e1.getEstado().getId().equals(e2.getEstado().getId()))
				|| (!esFaseFinal(e1.getEstado().getId()) && !esFaseFinal(e2.getEstado().getId()))
				|| (esFaseFinal(e1.getEstado().getId()) && esFaseFinal(e2.getEstado().getId()))) {
			return e1.getFechaEvento().compareTo(e2.getFechaEvento());
		} else {
			if (esFaseFinal(e1.getEstado().getId()) && !esFaseFinal(e2.getEstado().getId()))
				return 1;
			else if (!esFaseFinal(e1.getEstado().getId()) && esFaseFinal(e2.getEstado().getId()))
				return -1;
			else
				return 0;
		}

	}

	/**
	* Método encargado de indicar si el estado de un evento corresponde a un estado final.
	* Indica si el estado de un evento deportivo es finalizado o suspendido.
	* @param idEstado Identificador del estado de un evento deportivo.
	* @return Devuelve true si es fase final o false si no lo es.
	*/
	private boolean esFaseFinal(Long idEstado) {
		return idEstado.equals(Global.EVENTO_FINALIZADO) || idEstado.equals(Global.EVENTO_SUSPENDIDO);
	}

}