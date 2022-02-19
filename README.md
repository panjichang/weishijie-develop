#Preview
##Demo
You can click here to [download apk .](https://github.com/panjichang/weishijie-develop/blob/master/app/app-release-v4.4.6-c446.apk?raw=true)

##Screenshots
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-35-39.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-35-59.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-36-15.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-36-27.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-37-07.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-37-29.jpeg)
![Image](https://raw.githubusercontent.com/panjichang/weishijie-develop/master/Srceenshot/Screenshot_2016-01-26-14-37-43.jpeg)



 public static class Room implements Cloneable{
        public String roomNo; //房间号
        public List<String> eqTvs;//同房间TV
        public String targetTv; //目标TV
        public List<String> neTvs;//不同房间TV
        public int volume; //音量

        @Override
        protected Room clone() throws CloneNotSupportedException {
            return (Room) super.clone();
        }
    }

    public static void main(String[] args) {

        Map<String,List<String>> eqTvMap=new HashMap<>();
        List<String> eqTV1=new ArrayList<>();
        eqTV1.add("TV1");
        eqTvMap.put("卧室1-1",eqTV1);

        List<String> eqTV2=new ArrayList<>();
        eqTV2.add("TV1");
        eqTV2.add("TV2");
        eqTvMap.put("卧室1-2",eqTV2);

        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(eqTvMap));


        Map<String,List<String>> neTvMap=new HashMap<>();
        List<String> neTV1=new ArrayList<>();
        neTV1.add("TV2");
        neTvMap.put("卧室1-1",eqTV1);

        List<String> neTV2=new ArrayList<>();
        neTV2.add("TV3");
        neTvMap.put("卧室1-2",neTV2);

        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(neTvMap));

        //合并数据
        List<Room> merge = merge(eqTvMap, neTvMap);

        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(merge));

        //克隆数据
        List<Room> rooms1 = handleRoom(merge);

        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(rooms1));
    }


    public static List<Room> merge(Map<String,List<String>> eqTvMap,Map<String,List<String>> neTvMap){
        List<Room> roomList=new ArrayList<>();
        for (String key:eqTvMap.keySet()){
            List<String> neTv = neTvMap.get(key);
            List<String> eqTvs = eqTvMap.get(key);
            for (String tv:eqTvs){
                Room room=new Room();
                room.roomNo=key;
                room.targetTv=tv;
                room.neTvs=neTv;
                room.eqTvs=eqTvs;
                roomList.add(room);
            }
        }
        return roomList;
    }


    public static List<Room> handleRoom(List<Room> rooms){
        List<Room> handleRooms=new ArrayList<>();
        for (Room room:rooms){
            handleRooms.add(room);
            for (int i=0;i<4;i++){
                try {
                    Room clone = room.clone();
                    handleRooms.add(clone);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return handleRooms;
    }
