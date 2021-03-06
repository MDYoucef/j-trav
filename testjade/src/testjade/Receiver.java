package testjade;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.introspection.ACLMessage;

public class Receiver extends Agent {
	int x = 0, y = 0;

	protected void setup() {
		
		AID[] lesAgents = { new AID("acheteur", AID.ISLOCALNAME) };
		
		System.out.println("c'est " + getAID().getName() + " i'm ready.");
		
		jade.lang.acl.ACLMessage init = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.REQUEST);
		init.setContent("je suis pret");
		init.addReceiver(lesAgents[0]);
		send(init);
		
		addBehaviour(new TickerBehaviour(this,1000) {

			@Override
			public void onTick() {
				jade.lang.acl.ACLMessage partie1 = receive();

				jade.lang.acl.ACLMessage attente = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.REQUEST);
				jade.lang.acl.ACLMessage reponse = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);

				if (partie1 != null) {

					if (partie1.getConversationId().equals("debut")) {
						System.out.println("coucou");
						System.out.println(partie1.getContent());
						x = Integer.parseInt(partie1.getContent());
						attente.setContent("j'ai reçu le 1er chiffre");
						attente.addReceiver(partie1.getSender());

						send(attente);

					}

					else if (partie1.getConversationId().equals("finale")) {
						System.out.println("bouh");
						System.out.println(partie1.getContent());
						y = Integer.parseInt(partie1.getContent());
						int somme = x + y;
						reponse.setContent("" + somme);
						reponse.addReceiver(partie1.getSender());
						send(reponse);

					}
				} else {
					System.out.println("block sommmeur");
					block();
				}

			}

		}

		);

	}

}
