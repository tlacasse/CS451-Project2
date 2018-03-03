///process_buffer(buffer)

var buffer = argument0;

var type = buffer_read(buffer, buffer_s8);

var str = string(type);

switch(type){
    case 1:
        var visitors = buffer_read(buffer, buffer_s32);
        var cashiers = buffer_read(buffer, buffer_s32);
        str += string(visitors) + " " + string(cashiers);
        for(var i = 0; i < visitors; i++){
            str += "#   " ;
            str += " " + string(buffer_read(buffer, buffer_s32));
            str += " " + string(buffer_read(buffer, buffer_s32));
            str += " " + string(buffer_read(buffer, buffer_s32));
            str += " " + string(buffer_read(buffer, buffer_s32));
        }
        for(var i = 0; i < cashiers; i++){
            str += "#   " ;
            str += " " + string(buffer_read(buffer, buffer_s32));
            str += " " + string(buffer_read(buffer, buffer_s32));
            str += " " + string(buffer_read(buffer, buffer_s32));
        }
        break;
}

message = str + "#" + message;
