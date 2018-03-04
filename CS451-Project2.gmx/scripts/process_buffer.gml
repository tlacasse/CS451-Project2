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
        maxitems = buffer_read(buffer, buffer_s32);
        PLACE_GAP_Y = (room_width - (2 * PLACE_START_Y)) / (maxitems + 2);
        break;
}
