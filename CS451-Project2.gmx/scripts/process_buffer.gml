///process_buffer(buffer)

var buffer = argument0;

var type = buffer_read(buffer, buffer_s8);

switch(type){
    case Code.RESTART:
        game_restart();
        break;
    case Code.PEOPLE:
        for(var i = 0; i < COUNT_VISITORS; i++){
            var _id = buffer_read(buffer, buffer_s16);
            var _items = buffer_read(buffer, buffer_s16);
            var _desired = buffer_read(buffer, buffer_s16);
            var _status = buffer_read(buffer, buffer_s16);
            update_visitor(_id, _items, _desired, _status);
        }
        for(var i = 0; i < COUNT_CASHIERS; i++){
            var _id = buffer_read(buffer, buffer_s16);
            var _items = buffer_read(buffer, buffer_s16);
            var _desired = buffer_read(buffer, buffer_s16);
        }
        var size = buffer_read(buffer, buffer_s32);
        for(var i = 0; i < size; i++){
            update_queue(buffer_read(buffer, buffer_s16), i);
        }
        break;
    case Code.SIZE:
        MAXITEMS = buffer_read(buffer, buffer_s32);
        COUNT_VISITORS = buffer_read(buffer, buffer_s32);
        COUNT_CASHIERS = buffer_read(buffer, buffer_s32);
        QUEUE_SIZE = buffer_read(buffer, buffer_s32);
        
        var base = square_base(MAXITEMS + 1);
        ITEM_X = base;
        ITEM_Y = base;
        if (MAXITEMS + 1 > base * base){
            ITEM_X++;
        }
        ITEM_WIDTH = PLACE_DIV_X div ITEM_X;
        ITEM_HEIGHT = PLACE_DIV_Y div ITEM_Y;
        for(var i = 0; i < MAXITEMS + 1; i++){
            ITEMPOSITIONS[i, 0] = i % ITEM_X;
            ITEMPOSITIONS[i, 1] = i div ITEM_X;
            if (ITEMPOSITIONS[i, 1] % 2 == 1){
                ITEMPOSITIONS[i, 0] = ITEM_X - 1 - ITEMPOSITIONS[i, 0];
            }
            ITEMPOSITIONS[i, 0] = ITEMPOSITIONS[i, 0] * ITEM_WIDTH;
            ITEMPOSITIONS[i, 1] = ITEMPOSITIONS[i, 1] * ITEM_HEIGHT;
        }
        PLACE_ITEM_R = ITEM_HEIGHT * 0.95;

        base = square_base(COUNT_VISITORS);
        PERSON_X = base;
        PERSON_Y = base;
        if (MAXITEMS + 1 > base * base){
            ITEM_X++;
        }
        PERSON_WIDTH = (ITEM_WIDTH * 0.9) div PERSON_X;
        PERSON_HEIGHT = (ITEM_HEIGHT * 0.9) div PERSON_Y;
        
        QUEUEWIDTH = room_width div QUEUE_SIZE;
        break;
}
