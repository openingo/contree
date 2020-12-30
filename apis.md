# 接口文档

- 节点添加

  - Request

    - POST `/tree/node/add`

    - Body

      ```json
      {
      	"rootNodeId": "0", 
      	"treeCode": "树编码，必填", 
      	"nodeIcon": "",
      	"nodeName": "节点名称，必填", 
      	"nodeOrder": "节点初始顺序，必填，数字", 
      	"nodeTips": "",
      	"nodeExtension": {},
        "reorderNodes":[
          {
      			"nodeId": "树节点id，必填，数字", 
            "rootNodeId":"父节点id，变动时提供",
      			"nodeOrder": "树节点新顺序，必填，数字"
      		}
        ]
      }
      ```
      
      > 当节点在树的中间某处添加时，需要提供reorderNodes，便于将后续节点的顺序一并更新。

  - Response

    ```json
    {
    	"sc": 0,
    	"sm": "ok"
    }
    ```

- 节点编辑

  - Request

    - PUT `/tree/node` or POST `/tree/node/edit`

    - Body

      ```json
      {
      	"treeCode": "树编码，必填", 
      	"nodeId": "节点id，必填，数字", 
      	"nodeVisible": true,
      	"nodeExtension": {},
      	"rootNodeId": "0",
      	"nodeIcon": "",
      	"nodeName": "",
      	"nodeTips": "",
      	"nodeOrder": ""
      }
      ```

  - Response

    ```json
    {
    	"sc": 0,
    	"sm": "ok"
    }
    ```

- 节点删除

  - Request

    - DELETE `/tree/node` or POST `/tree/node/delete`

    - Body

      ```json
      {
      	"treeCode": "树编码，必填", 
      	"nodeId": 0,
      	"mode": "删除模式，必填。'cascade'级联删除各级节点；'non-sons'没有子节点时删除，有时不能删除" 
      }
      ```

  - Response

    ```json
    {
    	"sc": 0,
    	"sm": "ok"
    }
    ```

- 节点重排序

  - Request

    - PUT `/tree/node/reorder` or POST `/tree/node/reorder`

    - Body  

      ```json
      {
      	"treeCode": "树编码，必填", 
      	"reorderNodes": [
      		{
      			"nodeId": "树节点id，必填，数字", 
            "rootNodeId":"父节点id，变动时提供",
      			"nodeOrder": "树节点新顺序，必填，数字"
      		}
      	]
      }
      ```

  - Response

    ```json
    {
    	"sc": 0,
    	"sm": "ok"
    }
    ```

- 节点获取

  - Request
  
    - GET `/tree/node/list`
    - Params
      - `treeCode`树编码，必填
      - `nodeName`节点名称，用于模糊过滤
      - `rootNodeId`父节点id，必填，默认为0
      - `fetchType`获取数据类型，必填，"full"完整数据；"sons"仅第一级子节点。用于懒加载。
    
  - Response
  
    - `nodeName`不为空时
  
      ```json
      {
      	"sc": 0,
      	"sm": "ok",
      	"data": {
      		"nodeCount": 1,
      		"nodes": [{
      			"nodeId": 1,
      			"rootNodeId": 0,
      			"treeCode": "abc",
      			"nodeIcon": "...jpg",
      			"nodeColor": "red",
      			"nodeName": "a",
      			"nodeVisible": true,
      			"nodeOrder": 1,
      			"nodeTips": "tips",
      			"nodeExtension": {},
      			"createTime": "2020-12-18T09:49:12",
      			"updateTime": "2020-12-18T09:49:12"
      		}]
      	}
      }
      ```
  
    - `nodeName`为空时
  
      ```json
      {
      	"sc": 0,
      	"sm": "ok",
      	"data": {
      		"nodeCount": 12,
      		"nodes": [{
      			"nodeId": 1,
      			"rootNodeId": 0,
      			"treeCode": "abc",
      			"nodeIcon": "...jpg",
      			"nodeColor": "red",
      			"nodeName": "a",
      			"nodeVisible": true,
      			"nodeOrder": 1,
      			"nodeTips": "tips",
      			"nodeExtension": {},
      			"createTime": "2020-12-18T09:49:12",
      			"updateTime": "2020-12-18T09:49:12",
      			"sonCount": 1,
      			"sonNodes": [{
      				"nodeId": 1,
      				"rootNodeId": 0,
      				"treeCode": "abc",
      				"nodeIcon": "...jpg",
      				"nodeColor": "red",
      				"nodeName": "a",
      				"nodeVisible": true,
      				"nodeOrder": 1,
      				"nodeTips": "tips",
      				"nodeExtension": {},
      				"createTime": "2020-12-18T09:49:12",
      				"updateTime": "2020-12-18T09:49:12",
      				"sonCount": 0,
      				"sonNodes": null
      			}]
      		}]
      	}
      }
      ```