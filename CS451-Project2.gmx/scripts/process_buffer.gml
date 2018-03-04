///process_buffer(buffer)

var buffer = argument0;

var type = buffer_read(buffer, buffer_s8);

switch(type){
    case 0:
        game_restart();
        break;
    case 1:
        var visitors = buffer_read(buffer, buffer_s32);
        var cashiers = buffer_read(buffer, buffer_s32);
        for(var i = 0; i < visitors; i++){
            var _id = buffer_read(buffer, buffer_s32);
            var _items = buffer_read(buffer, buffer_s32);
            var _desired = buffer_read(buffer, buffer_s32);
            var _status = buffer_read(buffer, buffer_s32);
            update_visitor(_id, _items, _desired, _status);
        }
        break;
    case 2:
        MAXITEMS = buffer_read(buffer, buffer_s32);
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
        
        var visitors = buffer_read(buffer, buffer_s32);
        base = square_base(visitors);
        PERSON_X = base;
        PERSON_Y = base;
        if (MAXITEMS + 1 > base * base){
            ITEM_X++;
        }
        PERSON_WIDTH = (ITEM_WIDTH * 0.9) div PERSON_X;
        PERSON_HEIGHT = (ITEM_HEIGHT * 0.9) div PERSON_Y;
        break;
}
