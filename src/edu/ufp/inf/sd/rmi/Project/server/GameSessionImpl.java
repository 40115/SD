package edu.ufp.inf.sd.rmi.Project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI{
   private final UtilRI Util;
   private final ProjectMainImpl PM;
   private final String Token;
    public GameSessionImpl (ProjectMainImpl pm,UtilRI util,String token) throws RemoteException {
        super();
        this.PM=pm;
        this.Util=util;
        this.Token=token;
    }
    // Remove o jogador da base de dados

    @Override
    public void LogOut() throws RemoteException {
        this.PM.users.remove(Util);
    }


    // Permite conectar a um jogo, listar os jogos, criar um jogo e fazer o log out do jogo

    @Override
    public String Connect() throws RemoteException {
        if (this.PM.Valid(this.Token,this.Util)) {
            System.out.println(this.Util.getEmail());
            return "\nGame Froogger...\n" + "Select An option:\n" + "1-ListGames\n" + "2-Join Game\n" +"3-Create Game\n" + "4-Log Out\n";
        }
        return  "\nInvalid";
    }
    // Lista os jogos presentes na ArrayList

    public String List_Games()throws RemoteException{
        if (this.PM.Valid(this.Token,this.Util)) {
            StringBuilder j = new StringBuilder("List of Games:\n");
            for (int i = 0; i < this.PM.Game.size(); i++) {
                j.append(i).append("- ");
                for (UtilRI k : this.PM.Game.get(i).getUtil()) {
                    j.append(k.getEmail()).append(" ");

                }
                j.append("\n ");
            }
            return j.toString();
        }
        return "Token not valied";
    }
    // Cria um jogo e adiciona a ArrayList

    public boolean Create_Game(int Id) throws RemoteException{
        Remove_Game();
        if (this.PM.Valid(this.Token,this.Util)) {
            for (int i = 0; i < this.PM.Game.size(); i++) {
                for (int j = 0; j < this.PM.Game.get(i).getUtil().size(); j++) {
                    if (Objects.equals(this.PM.Game.get(i).getUtil().get(j).getEmail(), Util.getEmail())) {
                        return false;
                    }
                }
            }
            this.PM.Game.add(new FroggerGameImpl(this.PM, Id));
            return true;
        }
        return false;
    }
    // Junta o jogador ao jogo escolhido

    public FroggerGameRI join_Game(Integer I) throws RemoteException {
        Remove_Game();
        if (this.PM.Valid(this.Token,this.Util)) {
            if (this.PM.Game.size() < I) return null;
            for (int i = 0; i < this.PM.Game.size(); i++) {
                for (int j = 0; j < this.PM.Game.get(i).getUtil().size(); j++) {
                    if (Objects.equals(this.PM.Game.get(i).getUtil().get(j).getEmail(), Util.getEmail())) {
                        return null;
                    }
                }
            }
            return this.PM.Game.get(I);
        }
        return null;
    }
    // Verifica se existe jogos começados no servidor


    // Verifica se existe jogos no servidor


    public UtilRI getUtil()throws RemoteException {
        return Util;
    }

    public ProjectMainImpl getPM() throws RemoteException{
        return PM;
    }

    public String getToken()throws RemoteException {
        return Token;
    }
    public void Remove_Game() throws RemoteException {

        for (int i = 0; i <this.PM.Game.size() ; i++) {
            if (this.PM.Game.get(i).get_State().isHAsended()){
                this.PM.Game.remove(i);
                i--;
            }
        }

    }
}
