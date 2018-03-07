///update_visitor(id, items, desired_items, status)

var obj = ds_map_find_value(Client.PEOPLE, argument0);

if (is_undefined(obj)){
    _Visitor(argument0, argument1, argument2, argument3);
} else {
    obj.items = argument1;
    obj.desired = argument2;
    obj.status = argument3;
    if (obj.status == VisitorStatus.SHOPPING){
        var _gotox = Client.ITEMPOSITIONS[obj.items, 0] + obj.disp_x;
        var _gotoy = Client.ITEMPOSITIONS[obj.items, 1] + obj.disp_y;
        change_goto(obj, _gotox, _gotoy);
    }
    if (obj.status == VisitorStatus.DONE){
        change_goto(obj, room_width * 1.2, 
            ternary(ds_queue_size(obj.gotoy) == 0, obj.y, ds_queue_tail(obj.gotoy))
        );
    }
}
