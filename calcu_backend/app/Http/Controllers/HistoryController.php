<?php

namespace App\Http\Controllers;

use App\Models\History;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class HistoryController extends Controller
{

    public function store(Request $request)
    {
        $user = Auth::user(); // Gets the currently authenticated user
        $expression = $request->input('expression');
        $result = $request->input('result');
    
        $history = new History();
        $history->user_id = $user->id; // Dynamically tied to the logged-in user
        $history->expression = $expression;
        $history->result = $result;
        $history->save();
    
        return response()->json([
            'message' => 'History saved successfully',
            'data' => $history
        ]);
    }

    public function index()
    {
        $user = Auth::user(); 
        
        $historyRecords = History::where('user_id', $user->id)->get();
    
        return response()->json([
            'message' => 'History fetched',
            'data' => $historyRecords
        ]);
    }

    public function clear()
    {
        $user = Auth::user(); 

        History::where('user_id', $user->id)->delete();

        return response()->json([
            'message' => 'History cleared'
        ]);
    }

    
}

