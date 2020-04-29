package tarjans;

import java.util.*;

public class CriticalConnectionsNetwork {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> result = new ArrayList<>();
        if(connections.isEmpty()){
            return result;
        }else {
            Map<Integer, Set<Connection>> serverAdjacentList = new HashMap<>();

            //Mutating: This is intial state will remove not critical connections
            Set<List<Integer>> criticalConnections = new HashSet<>();

            for (List<Integer> connectionAsList : connections) {
                Connection connection = Connection.a(connectionAsList);
                criticalConnections.add(connectionAsList);
                Set<Connection> sAdjacentList = Optional.ofNullable(serverAdjacentList.get(connection.s)).orElseGet(() -> new HashSet<>());
                Set<Connection> dAdjacentList = Optional.ofNullable(serverAdjacentList.get(connection.d)).orElseGet(() -> new HashSet<>());
                sAdjacentList.add(connection);
                dAdjacentList.add(connection);
                serverAdjacentList.put(connection.s, sAdjacentList);
                serverAdjacentList.put(connection.d, dAdjacentList);
            }

            //Mutating
            Stack<UnitOfWork> work = new Stack<UnitOfWork>();
            //Mutating
            Set<Integer> visted = new HashSet<>();

            Connection firstConnection = Connection.a(connections.get(0));
            UnitOfWork start = UnitOfWork.a(firstConnection.s, new Stack<ServerConnectionPair>(), false);
            work.add(start);

            while(!work.isEmpty()){
                UnitOfWork currentUnitOfWork = work.pop();
                if (currentUnitOfWork != null){

                    if(!currentUnitOfWork.backtracking){
                        Integer currentServer = currentUnitOfWork.currentServer;
                        Boolean visited = visted.contains(currentServer);
                        if (!visited){
                            visted.add(currentServer);
                            Set<Connection> adjacentList = Optional.ofNullable(serverAdjacentList.get(currentServer)).orElseGet(() -> new HashSet<>());

                            for (Connection adjacentConnection : adjacentList){
                                Integer adjcentServer = adjacentConnection.getAdjacent(currentServer);
                                Boolean visitedAjacent = visted.contains(adjcentServer);
                                Stack<ServerConnectionPair> newTrack = (Stack<ServerConnectionPair>) currentUnitOfWork.track.clone();
                                newTrack.add(ServerConnectionPair.a(currentServer, adjacentConnection));

                                if(currentUnitOfWork.previousServer().isPresent() && currentUnitOfWork.previousServer().get() != adjcentServer && visitedAjacent){
                                    UnitOfWork newUnitOfWork = UnitOfWork.a(adjcentServer, newTrack, true);
                                    work.add(newUnitOfWork);
                                }else{
                                    UnitOfWork newUnitOfWork = UnitOfWork.a(adjcentServer, newTrack, false);
                                    work.add(newUnitOfWork);
                                }
                            }
                        }


                    } else {
                        Stack<ServerConnectionPair> newTrack = (Stack<ServerConnectionPair>) currentUnitOfWork.track.clone();
                        Integer visitedServer = currentUnitOfWork.currentServer;
                        if(!newTrack.isEmpty()){
                            ServerConnectionPair headOfTrack = newTrack.pop();
                            if (headOfTrack.server != visitedServer){
                                UnitOfWork newUnitOfWork = UnitOfWork.a(visitedServer, newTrack, true);
                                work.add(newUnitOfWork);
                            }
                            criticalConnections.remove(headOfTrack.connection.connectionAsList);
                        }
                    }
                }
            }

            result.addAll(criticalConnections);
        }


        return result;
    }


    static class UnitOfWork{
        private final Integer currentServer;
        private final Stack<ServerConnectionPair> track;
        private final Boolean backtracking;

        private UnitOfWork(Integer currentServer, Stack<ServerConnectionPair> track, Boolean backtracking){
            this.currentServer = currentServer;
            this.track = track;
            this.backtracking = backtracking;
        }

        public static UnitOfWork a(Integer currentServer, Stack<ServerConnectionPair> track, Boolean backtracking) {
            return new UnitOfWork(currentServer, track, backtracking);
        }

        public String toString() {
            return "(s=" + currentServer + ", bt=" + backtracking + ", t=" + track + ")";
        }

        public Optional<Integer> previousServer() {
            if (!track.isEmpty()){
                return Optional.of(track.peek().server);
            }else {
                return Optional.empty();
            }
        }
    }


    static class Connection{
        private final Integer s;
        private final Integer d;
        final List<Integer> connectionAsList;

        private Connection(Integer s, Integer d, List<Integer> connection){
            this.s = s;
            this.d = d;
            this.connectionAsList = connection;
        }

        public static Connection a(List<Integer> connection) {
            if (connection.size() != 2) {
                throw new IllegalArgumentException("List should contain exactly two element");
            }
            Integer source = connection.get(0);
            Integer destination = connection.get(1);
            return new Connection(source, destination, connection);
        }

        public Integer getAdjacent(Integer server) {
            if (server.equals(s)){
                return d;
            }else if (server.equals(d)){
                return s;
            }else{
                throw new IllegalArgumentException("Connection " + this + " doesn't envolve the given server " + server);
            }
        }

        public String toString() {
            return "(" + s + "," + d + ")";
        }

    }

    static class ServerConnectionPair{
        final Connection connection;
        final Integer server;

        private ServerConnectionPair(Integer server, Connection connection){
            this.server = server;
            this.connection = connection;
        }

        public static ServerConnectionPair a(Integer server, Connection connection) {
            return new ServerConnectionPair(server, connection);
        }

        public String toString() {
            return "(s=" + server + ", c=" + connection + ")";
        }
    }
}
