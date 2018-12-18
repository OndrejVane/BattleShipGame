package sample;

public class Game {

    /*
    0 = voda;
    1 = lod;
    2 = lod_potopená
    3 = vystrel_vedle
     */
    public int[][] myPane = new int[GameConfig.paneSize][GameConfig.paneSize];

    /*
    0 = neznámo
    1 = potopená lod
    2 = vedle
     */
    public int[][] enemyPane = new int[GameConfig.paneSize][GameConfig.paneSize];
    public int shipsToPlace;
    public int playerLives;
    public int enemyLives;

    public Game(){

        for (int i = 0; i< GameConfig.paneSize; i++){
            for (int j = 0; j< GameConfig.paneSize; j++){
                myPane[i][j] = 0;
                enemyPane[i][j] = 0;
            }
        }
        shipsToPlace = GameConfig.numberOfShips;
        playerLives = GameConfig.numberOfShips;
        enemyLives = GameConfig.numberOfShips;
    }

    public boolean putShip(int x, int y){
        if (myPane[x-1][y-1] == 0){
            myPane[x-1][y-1] = 1;
            shipsToPlace--;
            return true;
        }else {
            return false;
        }

    }

    public void printMyPane(){
        for (int i = 0; i< GameConfig.paneSize; i++){
            System.out.print("| ");
            for (int j = 0; j< GameConfig.paneSize; j++){
                System.out.print(myPane[i][j]+" ");
            }
            System.out.print("|");
            System.out.println();
        }
    }


    public void initPanesAfterReconnect(String[] message){
        int counter = 3;
        for (int i = 0; i< GameConfig.paneSize; i++){
            for (int j = 0; j< GameConfig.paneSize; j++){
                myPane[i][j] = Integer.parseInt(message[counter]);
                counter++;

            }
        }
        playerLives = Integer.parseInt(message[counter]);
        counter++;

        for (int i = 0; i< GameConfig.paneSize; i++){
            for (int j = 0; j< GameConfig.paneSize; j++){
                enemyPane[i][j] = Integer.parseInt(message[counter]);
                counter++;

            }
        }

        enemyLives = Integer.parseInt(message[counter]);
        counter++;
    }

    public int getShipsToPlace(){
        return shipsToPlace;
    }
}
