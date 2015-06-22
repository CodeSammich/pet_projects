//
//  ViewController.swift
//  Calculator
//
//  Created by Samuel Zhang on 6/20/15.
//  Copyright (c) 2015 CodeUmbra. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var display: UILabel!
    
    var isTyping = false //In the middle of typing a number
    
    @IBAction func appendDigit(sender: UIButton) {
        let digit = sender.currentTitle!
        
        if isTyping {
            display.text = display.text! + digit
        } else {
            display.text = digit
            isTyping = true
        }
    }
    
    var operandStack = Array<Double>()
    
    @IBAction func enter() {
        isTyping = false
    }
}

