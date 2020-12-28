# 接口文档

- 节点添加

  - POST `/tree/node/add`

  - Body

    ```json
    {
    	"rootNodeId": "0", 
    	"treeCode": "", // 树编码，必填
    	"nodeIcon": "",
    	"nodeName": "", // 节点名称，必填
    	"nodeOrder": "", // 节点初始顺序，必填
    	"nodeTips": "",
    	"nodeExtension": {}
    }
    ```

- 节点编辑

  - PUT `/tree/node` or POST `/tree/node/edit`

  - Body

    ```json
    {
    	"treeCode": "", // 树编码，必填
    	"nodeId": 0, // 节点id，必填
    	"nodeVisible": true,
    	"nodeExtension": {},
    	"rootNodeId": "0",
    	"nodeIcon": "",
    	"nodeName": "",
    	"nodeTips": "",
    	"nodeOrder": ""
    }
    ```

- 节点删除

  - DELETE `/tree/node` or POST `/tree/node/delete`

  - Body

    ```json
    {
    	"treeCode": "", // 树编码，必填
    	"nodeId": 0,
    	"mode": "cascade" // 删除模式，必填。"cascade"级联删除各级节点；"non-sons"没有子节点时删除，有时不能删除
    }
    ```

- 节点重排序

  - PUT `/tree/node/reorder` or POST `/tree/node/reorder`

  - Body  

    ```json
    {
    	"treeCode": "", // 树编码，必填
    	"reorderNodes": [
    		{
    			"nodeId": 0, // 树节点id，必填
    			"nodeOrder": 0 // 树节点新顺序，必填
    		}
    	]
    }
    ```

- 节点获取

  - GET `/tree/node/list`
  - Params
    - `treeCode`树编码，必填
    - `nodeName`节点名称，用于模糊过滤
    - `rootNodeId`父节点id，必填，默认为0
    - `fetchType`获取数据类型，必填，"full"完整数据；"sons"仅第一级子节点。用于懒加载。