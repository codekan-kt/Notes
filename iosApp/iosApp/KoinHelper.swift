//
//  KoinHelper.swift
//  iosApp
//
//  Created by Efekan YILMAZ on 5.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//



import SwiftUI
import shared

class KoinHelper {
    static let shared = KoinHelper()
    private let koinInitializer: KoinInitializer!
    
    private init() {
        let driverFactory = DatabaseDriverFactory()
        self.koinInitializer = KoinInitializer(driverFactory: driverFactory)
        self.koinInitializer.doInitKoin()
    }
    
    func getNotesViewModel() -> NotesViewModel {
        return koinInitializer.getNotesViewModel()
    }
}


