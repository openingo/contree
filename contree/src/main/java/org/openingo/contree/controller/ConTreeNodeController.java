/*
 * MIT License
 *
 * Copyright (c) 2020 OpeningO Co.,Ltd.
 *
 *    https://openingo.org
 *    contactus(at)openingo.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.openingo.contree.controller;

import org.openingo.contree.biz.IConTreeNodeBiz;
import org.openingo.contree.vo.ConTreeNodeReorderVO;
import org.openingo.contree.vo.ConTreeNodeVO;
import org.openingo.contree.vo.VoValidatorGroups;
import org.openingo.contree.vo.list.ConTreeNodeListReqVO;
import org.openingo.jdkits.http.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * ConTreeNodeController
 *
 * @author Qicz
 */
@RestController
@RequestMapping("/tree/node")
public class ConTreeNodeController {

    @Autowired
    IConTreeNodeBiz conTreeNodeBiz;

    /**
     * 节点添加
     */
    @PostMapping("/add")
    @Validated(VoValidatorGroups.Insert.class)
    public RespData addNode(@Valid @RequestBody ConTreeNodeVO conTreeNodeVO) {
        this.conTreeNodeBiz.addNode(conTreeNodeVO);
        return RespData.success();
    }

    /**
     * 节点编辑
     */
    @PostMapping("/edit")
    @PutMapping("/")
    @Validated(VoValidatorGroups.Update.class)
    public RespData editNode(@Valid @RequestBody ConTreeNodeVO conTreeNodeVO) {
        this.conTreeNodeBiz.editNode(conTreeNodeVO);
        return RespData.success();
    }

    /**
     * 节点删除
     */
    @PostMapping("/delete")
    @DeleteMapping("/")
    @Validated(VoValidatorGroups.Delete.class)
    public RespData deleteNode(@Valid @RequestBody ConTreeNodeVO conTreeNodeVO) {
        this.conTreeNodeBiz.deleteNode(conTreeNodeVO.getTreeCode(), conTreeNodeVO.getNodeId());
        return RespData.success();
    }

    /**
     * 节点重排序
     */
    @PostMapping("/reorder")
    @PutMapping("/reorder")
    @Validated
    public RespData reorderNodes(@Valid @RequestBody ConTreeNodeReorderVO conTreeNodeReorderVO){
        this.conTreeNodeBiz.reorderNodes(conTreeNodeReorderVO);
        return RespData.success();
    }

    /**
     * 节点获取
     */
    @GetMapping("/list")
    public RespData listNode(ConTreeNodeListReqVO conTreeNodeListReqVO) {
        conTreeNodeListReqVO.validate();
        return RespData.success(this.conTreeNodeBiz.listNode(conTreeNodeListReqVO));
    }
}
