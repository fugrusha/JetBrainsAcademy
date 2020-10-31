interface Movable {
    int getX();

    int getY();

    void setX(int newX);

    void setY(int newY);
}

interface Storable {
    int getInventoryLength();

    String getInventoryItem(int index);

    void setInventoryItem(int index, String item);
}

interface Command {
    void execute();

    void undo();
}

class CommandMove implements Command {

    public CommandMove(Movable entity, int xMovement, int yMovement) {
        this.entity = entity;
        this.xMovement = xMovement;
        this.yMovement = yMovement;
        this.prevX = entity.getX();
        this.prevY = entity.getY();
    }

    Movable entity;
    int xMovement;
    int yMovement;
    int prevX;
    int prevY;

    @Override
    public void execute() {
        prevX = entity.getX();
        prevY = entity.getY();
        entity.setX(prevX + xMovement);
        entity.setY(prevY + yMovement);
    }

    @Override
    public void undo() {
        entity.setX(prevX);
        entity.setY(prevY);
    }
}

class CommandPutItem implements Command {
    public CommandPutItem(Storable entity, String item) {
        this.entity = entity;
        this.item = item;
    }

    Storable entity;
    String item;
    int lastIndex;

    @Override
    public void execute() {
        int length = entity.getInventoryLength();
        for (int i = 0; i < length; i++) {
            if (entity.getInventoryItem(i) == null) {
                lastIndex = i;
                entity.setInventoryItem(i, item);
                break;
            }
        }
    }

    @Override
    public void undo() {
        entity.setInventoryItem(lastIndex, null);
    }
}

class Main {
    public static void main(String[] args) {

        StorableEntity storableEntity = new StorableEntity();
        storableEntity.myArray = new String[]{"1111", "222", "333", null};

        Command commandPutItem = new CommandPutItem(storableEntity, "ll");
        commandPutItem.execute();
        commandPutItem.undo();

        Movable movableEntity = new MovableEntity();
        movableEntity.setY(0);
        movableEntity.setX(0);

        Command moveCommand = new CommandMove(movableEntity, 4, -9);
        moveCommand.execute();
        moveCommand.undo();
    }
}

class StorableEntity implements Storable {

    String[] myArray;

    @Override
    public int getInventoryLength() {
        return myArray.length;
    }

    @Override
    public String getInventoryItem(int index) {
        return myArray[index];
    }

    @Override
    public void setInventoryItem(int index, String item) {
        myArray[index] = item;
    }
}

class MovableEntity implements Movable {

    int X;
    int Y;

    @Override
    public int getX() {
        return X;
    }

    @Override
    public int getY() {
        return Y;
    }

    @Override
    public void setX(int newX) {
        this.X = newX;
    }

    public void setY(int newY) {
        this.Y = newY;
    }
}
